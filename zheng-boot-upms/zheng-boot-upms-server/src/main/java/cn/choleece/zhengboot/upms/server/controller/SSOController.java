package cn.choleece.zhengboot.upms.server.controller;

import cn.choleece.upms.common.constant.UpmsResult;
import cn.choleece.upms.common.constant.UpmsResultConstant;
import cn.choleece.zhengboot.common.util.PropertiesFileUtil;
import cn.choleece.zhengboot.common.util.RedisUtil;
import cn.choleece.zhengboot.upms.client.shiro.session.UpmsSession;
import cn.choleece.zhengboot.upms.client.shiro.session.UpmsSessionDao;
import cn.choleece.zhengboot.upms.dao.model.UpmsSystem;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsSystemService;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author choleece
 * @description: 单点登录管理
 * @date 2018/8/1 12:34
 */
@RestController
@RequestMapping("/sso")
@Api(value = "单点登录管理", description = "单点登录管理")
public class SSOController {

    private static final Logger logger = LoggerFactory.getLogger(SSOController.class);

    /**
     * 全局会话key
     */
    private static final String ZHENG_UPMS_SERVER_SESSION_ID = "zheng-upms-server-session-id";

    /**
     * 全局会话key列表
     */
    private static final String ZHENG_UPMS_SERVER_SESSION_IDS = "zheng-upms-server-session-ids";

    /**
     * code key
     */
    private static final String ZHENG_UPMS_SERVER_CODE = "zheng-upms-server-code";

    @Autowired
    private IUpmsSystemService iUpmsSystemService;
    @Autowired
    private IUpmsUserService iUpmsUserService;
    @Autowired
    private UpmsSessionDao upmsSessionDao;

    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String serverSessionId = session.getId().toString();

        // 判断是否已登录，如果已登录，则回跳
        String code = RedisUtil.get(ZHENG_UPMS_SERVER_SESSION_ID + "_" + serverSessionId);

        // code检验值
        if (StringUtils.isNotBlank(code)) {
            // 回跳
            String backUrl = request.getParameter("backurl");
            String username = (String) subject.getPrincipal();
            if (StringUtils.isBlank(backUrl)) {
                backUrl = "/";
            } else {
                if (backUrl.contains("?")) {
                    backUrl += "&upms_code=" + code + "&upms_username=" + username;
                } else {
                    backUrl += "?upms_code=" + code + "&upms_username=" + username;
                }
            }
            logger.debug("认证中心账号通过，带code回跳:{}", backUrl);
            return "redirect:" + backUrl;
        }
        return "/sso/login.jsp";
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        if (StringUtils.isBlank(username)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_USERNAME, UpmsResultConstant.EMPTY_USERNAME.getMessage());
        }
        if (StringUtils.isBlank(password)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD, UpmsResultConstant.EMPTY_PASSWORD.getMessage());
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();

        // 判断是否已登录，如果已登录，则回跳，防止重复登录
        String hasCode = RedisUtil.get(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId);
        // code校验值
        if (StringUtils.isBlank(hasCode)) {
            // 使用shiro验证
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
            try {
                if (BooleanUtils.toBoolean(rememberMe)) {
                    usernamePasswordToken.setRememberMe(true);
                } else {
                    usernamePasswordToken.setRememberMe(false);
                }
                subject.login(usernamePasswordToken);
            } catch (UnknownAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_USERNAME, UpmsResultConstant.INVALID_USERNAME.getMessage());
            } catch (IncorrectCredentialsException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_PASSWORD, UpmsResultConstant.INVALID_PASSWORD.getMessage());
            } catch (LockedAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_ACCOUNT, UpmsResultConstant.INVALID_ACCOUNT.getMessage());
            }
            // 更新session 状态
            upmsSessionDao.updateStatus(sessionId, UpmsSession.OnlineStatus.on_line);
            // 全局会话sessionId列表，供会话管理
            RedisUtil.lpush(ZHENG_UPMS_SERVER_SESSION_IDS, sessionId.toString());
            // 默认验证账号密码正确，创建code
            String code = UUID.randomUUID().toString();
            // 全局会话的code
            RedisUtil.set(ZHENG_UPMS_SERVER_SESSION_ID + "_" + sessionId, code, (int)subject.getSession().getTimeout() / 1000);
            // code校验值
            RedisUtil.set(ZHENG_UPMS_SERVER_CODE + "_" + code, code, (int)subject.getSession().getTimeout() / 1000);
        }
        // 回跳登录前地址
        String backUrl = request.getParameter("backurl");
        if (StringUtils.isBlank(backUrl)) {
            UpmsSystem upmsSystem = iUpmsSystemService.selectUpmsSystemByName(PropertiesFileUtil.getInstance().get("app.name"));
            backUrl = null == upmsSystem ? "/" : upmsSystem.getBasepath();
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS, backUrl);
    }

    @ApiOperation(value = "校验code")
    @PostMapping("/code")
    public Object code(HttpServletRequest request) {
        String codeParam = request.getParameter("code");
        String code = RedisUtil.get(ZHENG_UPMS_SERVER_CODE + "_" + codeParam);
        if (StringUtils.isBlank(codeParam) || !codeParam.equals(code)) {
            return new UpmsResult(UpmsResultConstant.FAILED, "无效的code");
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS, code);
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        return "redirect:" + redirectUrl;
    }
}
