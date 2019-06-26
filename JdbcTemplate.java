package com.zh.jdbc;

import java.sql.*;
import java.util.logging.Handler;

public class JdbcTemplate {
    public <P,R> R execute(String sql, Handler<P,R> handler) {
        this.loadDriver();
        this.createConnection();
        this.createStatement();
        R t;
        if(sql.trim().toUpperCase().startsWith("SELECT")) {   //首先去掉空格，因为在数据库中有空格也是对的
            this.resultSet = this.executeQuery(sql);
            //处理结果
            t = handler.handler((P)this.resultSet);
        } else{
            Integer effect = this.executeUpdate(sql);
            t = handler.handler((P)effect);
        }
        this.close();
        return t;
    }


    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void createConnection() {
        String url="jdbc:mysql://127.0.0.1:3306/memo";
        try {
            this.connection= DriverManager.getConnection(url,"root","19913017AB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //3.创建命令
    private void createStatement() {
        try {
            this.statement=connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int executeUpdate(String sql) {
        try {
            return this.statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private ResultSet executeQuery(String sql) {
        try {
            return this.statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private  void close(){
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



    interface  Handler<P,R>{
        R handler(P p);
    }
}
