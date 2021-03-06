package cn.choleece.zhengboot.upms.client.interceptor;

import cn.choleece.zhengboot.common.util.RequestUtil;
import cn.choleece.zhengboot.upms.dao.model.UpmsLog;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志记录AOP实现
 * Created by choleece on 2018/7/22.
 */
@Aspect
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 开始时间
     */
    private long startTime = 0L;

    /**
     * 结束时间
     */
    private long endTime = 0L;

    @Before("execution(*.*.controller.*.*(..))")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {
        logger.debug("do before in service layer");
        startTime = System.currentTimeMillis();
    }

    @After("execution(*.*.controller.*.*(..))")
    public void doAfterInServiceLayer(JoinPoint joinPoint) {
        logger.debug("do after in service layer");
    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        UpmsLog upmsLog = new UpmsLog();

        // 从竹节中获取操作名称、获取响应结果
        Object result = pjp.proceed();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            upmsLog.setDescription(log.value());
        }
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
            String[] permisstions = requiresPermissions.value();
            if (permisstions != null && permisstions.length > 0) {
                upmsLog.setPermissions(permisstions[0]);
            }
        }

        endTime = System.currentTimeMillis();
        logger.debug("do around result = {}", result, endTime - startTime);

        upmsLog.setBasePath(RequestUtil.getBasePath(request));
        upmsLog.setIp(RequestUtil.getIpAddr(request));
        upmsLog.setMethod(request.getMethod());
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            upmsLog.setParameter(request.getQueryString());
        } else {
            upmsLog.setParameter(ObjectUtils.toString(request.getParameterMap()));
        }
        upmsLog.setResult(JSON.toJSONString(request));
        upmsLog.setSpendTime((int) (endTime - startTime));
        upmsLog.setStartTime(startTime);
        upmsLog.setUri(request.getRequestURI());
        upmsLog.setUrl(ObjectUtils.toString(request.getRequestURL()));
        upmsLog.setUserAgent(request.getHeader("User-Agent"));
        upmsLog.setUsername(ObjectUtils.toString(request.getUserPrincipal()));
        return result;
    }
}
