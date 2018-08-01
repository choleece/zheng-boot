package cn.choleece.zhengboot.upms.server.interceptor;

import cn.choleece.zhengboot.common.util.PropertiesFileUtil;
import cn.choleece.zhengboot.upms.dao.model.UpmsUser;
import cn.choleece.zhengboot.upms.rpc.api.IUpmsApiService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author choleece
 * @description: 登录信息拦截器
 * @date 2018/8/1 12:26
 */
public class UpmsInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UpmsInterceptor.class);
    private static final String ZHENG_OSS_ALIYUN_OSS_POLICY = PropertiesFileUtil.getInstance("zheng-oss-client").get("zheng.oss.aliyun.oss.policy");

    @Autowired
    private IUpmsApiService iUpmsApiService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("ZHENG_OSS_ALIYUN_OSS_POLICY", ZHENG_OSS_ALIYUN_OSS_POLICY);
        // 过滤ajax
        if (null != request.getHeader("X-Request-With") && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return true;
        }
        // 登录信息
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUser upmsUser = iUpmsApiService.selectUpmsUserByUsername(username);
        request.setAttribute("upmsUser", upmsUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
