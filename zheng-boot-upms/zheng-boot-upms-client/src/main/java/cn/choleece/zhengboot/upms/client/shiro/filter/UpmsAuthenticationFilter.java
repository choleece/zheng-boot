package cn.choleece.zhengboot.upms.client.shiro.filter;

import cn.choleece.upms.common.constant.UpmsConstant;
import cn.choleece.zhengboot.common.util.PropertiesFileUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author choleece
 * @description: 重写authc过滤器
 * @date 2018/7/23 12:49
 */
public class UpmsAuthenticationFilter extends AuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(UpmsAuthenticationFilter.class);

    /**
     * 局部会话key
     */
    private final static String ZHENG_UPMS_CLIENT_SESSION_ID = "zheng-upms-client-session-id";

    /**
     * 单点同一个code所有局部会话key
     */
    private final static String ZHENG_UPMS_CLIENT_SESSION_IDS = "zheng-upms-client-session-ids";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        // 判断请求类型
        String upmsType = PropertiesFileUtil.getInstance("zheng-boot-upms-client").get("zheng.upms.type");
        session.setAttribute(UpmsConstant.UPMS_TYPE, upmsType);
        if ("client".equals(upmsType)) {
            return validateClient(request, response);
        }
        if ("server".equals(upmsType)) {
            return subject.isAuthenticated();
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }

    /**
     * 认证中心登录成功待会code
     * @param request
     * @param response
     * @return
     */
    private boolean validateClient(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        int timeOut = (int) session.getTimeout() / 1000;
        // 判断局部会话是否登录

        return false;
    }
}
