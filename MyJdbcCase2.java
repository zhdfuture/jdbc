package com.zh.jdbc;

import java.sql.*;
import java.util.Collection;

public class MyJdbcCase2 {
    public static void main(String[] args) {
        //1.加载数据库的jdbc驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //2.JDBC url协议
            String url="jdbc:mysql://127.0.0.1:3306/memo?user=root&password=19913017AB";
            String sql="select id,name,created_time,modify_time from memo_group";
            //因为他实现了AutoClosable接口，因此是自动关闭，
            //因此使用try-with-resource的方式来关闭结果集、命令、连接
            try(Connection connection= DriverManager.getConnection(url);
                Statement statement=connection.createStatement();
                ResultSet resultSet=statement.executeQuery(sql);
                ){
                while(resultSet.next()){
                    int id=resultSet.getInt("id");
                    String name=resultSet.getString("name");
                    Timestamp created_time=resultSet.getTimestamp("created_time");
                    Timestamp modify_time=resultSet.getTimestamp("modify_time");
                    System.out.println(id+""+name+""+created_time+""+modify_time);
                }
                }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
