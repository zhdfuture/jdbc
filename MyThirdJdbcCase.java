package com.zh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyThirdJdbcCase {
    public static void main(String[] args) {
        try {
            //1.加载数据库的jdbc驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.JDBC url协议
            String url = "jdbc:mysql://127.0.0.1:3306/memo?user=root&password=19913017AB";
            Connection connection = DriverManager.getConnection(url);
            //3.创建命令
            Statement statement = connection.createStatement();
            //4.
            String sql = "delete from memo_group where id=1";
            int effect = statement.executeUpdate(sql);
            System.out.println(effect);
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}