package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static{
        //静态代码块，类加载时就被初始化
        //为上面的私有变量赋值，即：连接数据库参数设置
        Properties properties = new Properties();
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    //获得数据库连接
    public static Connection getConnection() {
        Connection connection = null;
        //1. 注册驱动
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //查询的sql方法
    public static ResultSet executeQuery(Connection conn, String sql, Object[] params, ResultSet rs,PreparedStatement preSta) throws SQLException {
        //查询方法
        //这里的sql是没有完全填写完整的sql
        //需要通过params进行参数填充
        preSta = conn.prepareStatement(sql);
        for (int i=0;i<params.length;i++){
            preSta.setObject(i+1,params[i]);
            //sql语句中，索引应该从1开始
        }
        rs = preSta.executeQuery();
        return rs;
        //这里的resultSet已经作为了参数传进来一遍，但又作为返回值传出去？
    }

    //增删改的sql方法
    public static int execute(Connection conn,String sql,Object[] params,PreparedStatement preSta) throws SQLException {
        preSta = conn.prepareStatement(sql);
        for (int i=0;i<params.length;i++){
            preSta.setObject(i+1,params[i]);
            //sql语句中，索引应该从1开始
        }
        int ans = preSta.executeUpdate();
        return ans;
    }

    //释放连接资源
    public static boolean realease(Connection conn,ResultSet rs,PreparedStatement preSta){
        boolean flag = true;
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (rs!=null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (preSta!=null){
            try {
                preSta.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
