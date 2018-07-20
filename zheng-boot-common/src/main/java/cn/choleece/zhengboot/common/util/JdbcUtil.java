package cn.choleece.zhengboot.common.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author choleece
 * @description: JDBC工具类
 * @date 2018/7/20 13:28
 */
public class JdbcUtil {
    private Connection conn;

    private PreparedStatement pstmt;

    private ResultSet rs;

    /**
     * 初始化数据库连接
     * @param driver
     * @param url
     * @param username
     * @param password
     */
    public JdbcUtil(String driver, String url, String username, String password) {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库链接成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean updateByParams(String sql, List params) throws SQLException {
         pstmt = conn.prepareStatement(sql);
         int index = 1;
         // 填充sql的占位符
        if (null != params && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        // execute返回受影响的行数，如果>0，则说明更新成功
        return pstmt.executeUpdate() > 0;
    }

    /**
     * 查询多条记录
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map> selectedByParams(String sql, List params) throws SQLException {
        List<Map> list = new ArrayList<Map>();
        int index = 1;
        pstmt = conn.prepareStatement(sql);
        // 填充sql的占位符
        if (null != params && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        rs = pstmt.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int colsLen = metaData.getColumnCount();
        while (rs.next()) {
            Map map = new HashMap(colsLen);
            for (int i = 0; i < colsLen; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object columnValue = rs.getObject(columnName);
                if (null == columnValue) {
                    columnValue = "";
                }
                map.put(columnName, columnValue);
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 释放数据库连接
     */
    public void release() {
        try {
            if (null != rs) {
                rs.close();
            }
            if (null != pstmt) {
                pstmt.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("释放数据库链接");
    }
}
