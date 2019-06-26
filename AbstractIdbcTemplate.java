package com.zh.jdbc;

import java.sql.*;

//因为有许多是不需要改动，因此采用模板设计模式
public abstract class AbstractIdbcTemplate {
    /*
   不变的：
   1.加载驱动
   2.获取连接
   3.创建命令
   4.关闭

   变的：
   1.SQL
   2.执行命令
   3.处理结果
   4.
    */
    public <T> T execute(String sql) {
        this.loadDriver();
        this.createConnection();
        this.createStatement();
        T t;
        if (sql.trim().toUpperCase().startsWith("SELECT")) {   //首先去掉空格，因为在数据库中有空格也是对的
            this.resultSet = this.executeQuery(sql);
            //处理结果
            t = this.handlerResult(resultSet);
        } else {
            int effect = this.executeUpdate(sql);
            t = this.handlerResult(effect);
        }
        this.close();
        return t;
    }



    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

//1.加载驱动
    private void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //2.创建连接
    private void createConnection(){
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
      //处理结果集
    public  abstract <T> T handlerResult(ResultSet resultSet) ;
    public abstract <T> T  handlerResult(int effect);
    //关闭资源
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

    public static void main(String[] args) {

        }
    }


