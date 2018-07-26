package cn.choleece.zhengboot.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * 资源文件读取工具
 * Created by choleece on 2018/7/27.
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    public SpringContextUtil() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据bean名称获取bean
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 根据bean名称获取指定类型的bean
     * @param beanName bean的名称
     * @param tClass 返回的bean类型,若类型不匹配,将抛出异常
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> tClass) {
        return context.getBean(beanName, tClass);
    }

    /**
     * 根据类型获取bean
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> tClass) {
        T t = null;
        Map<String, T> map = context.getBeansOfType(tClass);
        for (Map.Entry<String, T> entry : map.entrySet()) {
            t = entry.getValue();
        }
        return t;
    }

    /**
     * 是否包含bean
     * @param beanName
     * @return
     */
    public static boolean containsBean(String beanName) {
        return context.containsBean(beanName);
    }

    /**
     * bean是否是单例
     * @param beanName
     * @return
     */
    public static boolean isSingleton(String beanName) {
        return context.isSingleton(beanName);
    }

    /**
     * 获取Bean的类型
     * @param beanName
     * @return
     */
    public static Class getType(String beanName) {
        return context.getType(beanName);
    }
}
