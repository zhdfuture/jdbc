package com.zh.jdbc;

import java.sql.*;

public class MyFirstJdbcCase {
    /*1.加载数据库的jdbc驱动
    2.创建连接
    3.创建命令
    4.准备SQL语句
    5.执行SqL语句
    6.处理结果
    7.关闭结果
    8.关闭命令
    9.关闭连接
     */
    public static void main(String[] args) {
        //1.加载数据库的jdbc驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2.JDBC url协议
            //协议的格式：<databaseType>://<host>:<port>/<databaseName>?user=<name>&password=<password>

            String url="jdbc:mysql://localhost:3306/memo?user=root&password=19913017AB";
            try {
                Connection connection= DriverManager.getConnection(url);//使用 DriverManager连接
                //3.
                Statement statement=connection.createStatement();
                //4.
               String sql="select id,name,created_time,modify_time from memo_group";
               //5.
                ResultSet resultSet=statement.executeQuery(sql);   //statment.executeQuery(),返回值是ResultSet(结果集）
                //6.
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String name=resultSet.getString("name");
                    Timestamp created_time=resultSet.getTimestamp("created_time");
                    Timestamp modify_time=resultSet.getTimestamp("modify_time");
                    System.out.println(id+""+name+""+created_time+""+modify_time);
                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {         //受查时异常
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

