package cn.choleece.zhengboot.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by choleece on 2018/7/21.
 */
public class PropertiesFileUtil {

    /**
     * 当打开多个资源文件时，缓存资源文件
     */
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<>();

    /**
     * 打开文件时间，判断是否超时
     */
    private Date loadTime = null;

    /**
     * 资源文件
     */
    private ResourceBundle resourceBundle = null;

    /**
     * 默认资源文件名称
     */
    private static final String NAME = "config";

    /**
     * 超时时间
     */
    private static final Integer TIME_OUT = 60 * 1000;

    /**
     * 私有构造方法，创建单例
     * @param name
     */
    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil conf = configMap.get(name);
        // 判断资源不存在或者是否打开的资源文件是否超过1分钟
        if (null == conf || (System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    /**
     * 根据key读取value
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "";
        }
    }
}
