package cn.choleece.zhengboot.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Request 工具类
 * Created by choleece on 2018/7/23.
 */
public class RequestUtil {

    /**
     * 获取请求的basePath
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
        StringBuffer bathPath = new StringBuffer();
        String scheme = request.getScheme();
        String domain = request.getServerName();
        int port = request.getServerPort();
        bathPath.append(scheme).append("://").append(domain);
        if ("http".equalsIgnoreCase(scheme) && 80 != port) {
            bathPath.append(":").append(String.valueOf(port));
        } else if ("https".equalsIgnoreCase(scheme) && 443 != port) {
            bathPath.append(":").append(String.valueOf(port));
        }
        return bathPath.toString();
    }

    /**
     * 获取IP工具类，除了getRemoteAddr，其他IP均可伪造
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        // 网宿CND的真实IP
        String ip = request.getHeader("Cdn-Src-Ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // 蓝汛CDN的真实IP
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        ip = request.getRemoteAddr();
        return ip;

    }
}
