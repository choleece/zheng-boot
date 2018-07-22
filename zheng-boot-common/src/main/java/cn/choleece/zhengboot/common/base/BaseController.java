package cn.choleece.zhengboot.common.base;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.InvalidSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 控制器基类
 * Created by choleece on 2018/7/21.
 */
public abstract class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 统一异常处理
     * @param request
     * @param response
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String globalException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        logger.error("统一异常处理");
        request.setAttribute("ex", exception);

        if (null != request.getHeader("X-Requested-With") && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            request.setAttribute("requestHeader", "ajax");
        }
        if (exception instanceof UnauthenticatedException) {
            return "shiro 没有权限";
        }
        if (exception instanceof InvalidSessionException) {
            return "shiro 会话过期";
        }
        if (exception instanceof UnauthorizedException) {
            return "用户名或密码错误";
        }
        return "error";
    }
}
