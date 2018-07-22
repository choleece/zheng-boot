package cn.choleece.zhengboot.cms.dao;


import cn.choleece.zhengboot.common.util.MybatisGeneratorUtil;
import cn.choleece.zhengboot.common.util.PropertiesFileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author choleece
 * @description: cn.choleece.zhengboot.cms.dao
 * @date 2018/7/20 11:13
 */
public class CodeGenerator {
    private static String MODULE = "zheng-boot-cms";
    private static String DATABASE = "zheng-boot";
    private static String TABLE_PREFIX = "cms_";
    private static String PACKAGE_NAME = "cn.choleece.zhengboot.cms";
    private static String JDBC_DRIVER = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesFileUtil.getInstance("generator").get("generator.jdbc.password");
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();

    /**
     * 自动生成代码
     * @param args
     */
    public static void main(String[] args) throws Exception {
        MybatisGeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, TABLE_PREFIX, PACKAGE_NAME, LAST_INSERT_ID_TABLES);
    }
}
