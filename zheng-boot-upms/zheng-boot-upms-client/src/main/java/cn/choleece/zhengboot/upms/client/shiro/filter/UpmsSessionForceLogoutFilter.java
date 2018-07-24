package cn.choleece.zhengboot.upms.client.shiro.filter;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author choleece
 * @description: 强制退出会话过滤器
 * @date 2018/7/24 18:48
 */
public class UpmsSessionForceLogoutFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Session session = getSubject(servletRequest, servletResponse).getSession(false);
        if (session == null) {
            return true;
        }
        return session.getAttribute("FORCE_LOGOUT") == null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        getSubject(servletRequest, servletResponse).logout();
        String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
        WebUtils.issueRedirect(servletRequest, servletResponse, loginUrl);
        return false;
    }
}
