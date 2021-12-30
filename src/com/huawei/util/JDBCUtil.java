package com.huawei.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {
    
    //创建全局属性
    private static String className;
    private static String url;
    private static String username;
    private static String password;
    
    /**
     * 读取数据库配置文件
     */
    static {
        try {
            Properties pro = new Properties();
            InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            //读取配置文件获取信息
            pro.load(in);
            //根据Key值获取Value值
            className = pro.getProperty("DRIVER_CLASS");
            url = pro.getProperty("DB_URL");
            username = pro.getProperty("DB_USER");
            password = pro.getProperty("DB_PASSWORD");
            
        } catch (IOException e) {
            System.out.println("获取数据库配置文件信息错误");
            e.printStackTrace();
        }
    }
    
    /**
     * 加载数据库MySQL驱动
     */
    static {
        //加载驱动
        try {
            Class.forName(className);
        }catch(ClassNotFoundException e){
            System.out.println("驱动加载失败");
            e.printStackTrace();
        }
        
    }
    
    /**
     * 
     * @Title: getConn   
     * @Description: 获取数据库连接对象
     * @Author: XIE.YUXI   
     * @param: @return      
     * @return: Connection      
     * @throws
     */
    public static Connection getConn() {
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接错误！");
        }
    }
    
    /**
     * 
     * @Title: closeAll   
     * @Description: 关闭连接释放资源
     * @Author: XIE.YUXI   
     * @param: @param conn
     * @param: @param stmt
     * @param: @param rs      
     * @return: void      
     * @throws
     */
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * @Title: executeUpdate   
     * @Description: 执行SQL更新语句，包括增删改
     * @Author: XIE.YUXI   
     * @param: @param preparedSql
     * @param: @param param
     * @param: @return      
     * @return: int      
     * @throws
     */
    public static int executeUpdate(String preparedSql, Object ...param) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int key = 0;
        
        /* 处理SQL，执行SQL */
        try {
            // 得到数据库连接
            conn = getConn();
            // 得到preparedstatment对象
            pstmt = conn.prepareStatement(preparedSql);
            if(param != null) {
                for(int i = 0; i<param.length; i++) {
                    // 为编译sql设置参数
                    pstmt.setObject(i+1, param[i]);
                }
            }
            // 执行SQL语句
            pstmt.executeUpdate();
            
            pstmt=conn.prepareStatement(preparedSql,Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = pstmt.getGeneratedKeys();
            
            if(resultSet.next()) {
                // 获取主键返回
                key = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        return 0;
        
    }
}
