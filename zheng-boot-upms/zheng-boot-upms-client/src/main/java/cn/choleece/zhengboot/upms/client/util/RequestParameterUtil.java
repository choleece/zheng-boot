package cn.choleece.zhengboot.upms.client.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * request 参数工具类
 * Created by choleece on 2018/7/22.
 */
public class RequestParameterUtil {

    /**
     * 移除url中的code、username参数
     * @param request
     * @return
     */
    public static String parameterWithoutCode(HttpServletRequest request) {
        StringBuffer backUrl = request.getRequestURL();
        String params = "";
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            if ("!upms_code".equals(entry.getKey()) && !"upms_username".equals(entry.getKey())) {
                if ("".equals(params)) {
                    params = entry.getKey() + "=" + entry.getValue()[0];
                } else {
                    params += "&" + entry.getKey() + "=" + entry.getValue()[0];
                }

            }
        }
        if (!StringUtils.isBlank(params)) {
            backUrl = backUrl.append("?").append(params);
        }
        return backUrl.toString();
    }
}
