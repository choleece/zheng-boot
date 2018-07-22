package cn.choleece.zhengboot.admin.controller;

import cn.choleece.zhengboot.admin.database.UserBean;
import cn.choleece.zhengboot.admin.database.UserService;
import cn.choleece.zhengboot.cms.common.constant.CmsResult;
import cn.choleece.zhengboot.cms.common.constant.CmsResultConstant;
import cn.choleece.zhengboot.common.base.BaseController;
import cn.choleece.zhengboot.common.util.JWTUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author choleece
 * @description: cn.choleece.zhengboot.admin.controller
 * @date 2018/7/22 15:41
 */
@RestController
public class WebController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public CmsResult login(@RequestParam String username, @RequestParam String password) {
        UserBean userBean = userService.getUser(username);
        if (userBean == null) {
            return new CmsResult(CmsResultConstant.NO_USER, "用户不存在");
        }
        if (userBean.getPassword().equals(password)) {
            return new CmsResult(CmsResultConstant.SUCCESS, JWTUtil.sign(username, password));
        }
        throw new UnauthorizedException();
    }

    @GetMapping("/article")
    public CmsResult article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            logger.info("you are already logged in");
            return new CmsResult(CmsResultConstant.SUCCESS, null);
        }
        logger.info("you are guest");
        return new CmsResult(CmsResultConstant.SUCCESS, null);
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public CmsResult requireAuth() {
        logger.info("yuo are authenticated");
        return new CmsResult(CmsResultConstant.SUCCESS, null);
    }

    @GetMapping("require_role")
    @RequiresRoles("admin")
    public CmsResult requireRole() {
        logger.info("you are visiting require_role");
        return new CmsResult(CmsResultConstant.SUCCESS, null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public CmsResult requirePermission() {
        logger.info("you are visiting permission require edit,view");
        return new CmsResult(CmsResultConstant.SUCCESS, null);
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CmsResult unauthorized() {
        logger.info("you are visit 401");
        return new CmsResult(CmsResultConstant.FAILED, null);
    }
}
