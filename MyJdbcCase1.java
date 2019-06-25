package com.zh.jdbc;

import java.sql.*;

public class MyJdbcCase1 {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1.加载数据库的jdbc驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.JDBC url协议
            String url = "jdbc:mysql://127.0.0.1:3306/memo?user=root&password=19913017AB";
            connection = DriverManager.getConnection(url);
            //3.创建命令
            statement = connection.createStatement();
            //4.准备SQL语句
            String sql = "select id,name,created_time,modify_time from memo_group";
            //5.执行SqL语句
            resultSet = statement.executeQuery(sql);
            //6.处理结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Timestamp created_time = resultSet.getTimestamp("created_time");
                Timestamp modify_time = resultSet.getTimestamp("modify_time");
                System.out.println(id + "" + name + "" + created_time + "" + modify_time);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //7.关闭
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}