package com.cn.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.Properties;

/**
 * @Author:zhangyang
 * @Date: 2020-7-13 10:45
 * @Version 1.0
 */
public class JDBCUtils {


    private static DataSource ds;

    static {
        try {
            Properties prop = new Properties();
            InputStream inputStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            prop.load(inputStream);
            inputStream.close();

            ds = DruidDataSourceFactory.createDataSource(prop);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取连接
    public static Connection getConnection() throws SQLException {
        return ds.getConnection() ;
    }


    //定义一个方法,获取存在的连接池
    public static  DataSource getDataSource(){
        return ds ;
    }

    //释放资源
    public static void close(Statement stmt,Connection conn){
        close(null,stmt,conn);
    }

    private static void close(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }





}
