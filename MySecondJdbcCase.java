package com.zh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySecondJdbcCase {
    public static void main(String[] args) {

        try {
            //1.加载数据库的jdbc驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.JDBC url协议
            String url="jdbc:mysql://127.0.0.1:3306/memo?user=root&password=19913017AB";
            Connection connection= DriverManager.getConnection(url);
            //3.创建命令
            Statement statement=connection.createStatement();
            //4.准备SQL语句
            String sql="insert into memo_group(name,created_time) values('张三','2019-06-24 22:31:00')";
            //5.执行SqL语句
            int effect=statement.executeUpdate(sql);  //executeUpdate返回值是int型
            //6.处理结果
            System.out.println(effect);
            //7.关闭结果
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
