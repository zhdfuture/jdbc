package com.zh.jdbc;

import java.sql.*;
import java.time.LocalDateTime;

public class JdbcCase6 {
    public static boolean createMemoInfo(MemoGroup memoGroup,MemoInfo memoInfo){
        Connection connection=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/memo";
            String user="root";
            String password="19913017AB";
            connection= DriverManager.getConnection(url,user,password);
                    //事务控制
            connection.setAutoCommit(false);  //设为false，程序员手动控制
            //事务要么成功，要么失败
            String memoGroupInsertSql="insert into memo_group(id,name,created_time)values (?,?,?)";
            String memoInfoInsertSql="insert into memo_info(id,group_id,title,content,created_time)values (?,?,?,?,?)";
            int effect1=-1;
            int effect2=-1;
            try(PreparedStatement preparedStatement=connection.prepareStatement(memoGroupInsertSql)){
                preparedStatement.setInt(1,memoGroup.getId());
                preparedStatement.setString(2,memoGroup.getName());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(memoGroup.getCreatedTime()));
                effect1=preparedStatement.executeUpdate();
            }
            try(PreparedStatement statement=connection.prepareStatement(memoInfoInsertSql)){
                statement.setInt(1,memoInfo.getId());
                statement.setInt(2,memoInfo.getGroupId());
                statement.setString(3,memoInfo.getTitle());
                statement.setString(4,memoInfo.getContent());
                statement.setTimestamp(5,Timestamp.valueOf(memoInfo.getCreatedTime()));
                effect2=statement.executeUpdate();
            }
            //正常执行完成后，根据结果确定是否提交或回滚
            if(effect1==1&&effect2==1){
                connection.commit();
                return true;
            }else{
                connection.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //执行失败，应该回滚
            if(connection!=null){
                try{
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally{
            if(connection!=null){
                try{
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
       return false;
    }

    public static void main(String[] args) {
        MemoGroup memoGroup=new MemoGroup();
        memoGroup.setId(7);
        memoGroup.setName("小小");
        memoGroup.setCreatedTime(LocalDateTime.now());

        MemoInfo memoInfo=new MemoInfo();
        memoInfo.setId(8);
        memoInfo.setGroupId(memoGroup.getId());
        memoInfo.setTitle("未来");
        memoInfo.setContent("既然选择远方，便只顾风雨兼程");
        memoInfo.setCreatedTime(LocalDateTime.now());
        boolean t=createMemoInfo(memoGroup,memoInfo);
        if(t){
            System.out.println("创建便签成功");
        }else{
            System.out.println("创建便签失败");
        }
    }
}
class MemoInfo{
private Integer id;
private Integer groupId;
private String title;
private String content;
private String isProtected;
private Color background;
private String isRemind;
private LocalDateTime remindTime;
private LocalDateTime createdTime;
private LocalDateTime modifyTime;
    enum Color{WHITE,RED,BLUE,GREEN;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsProtected() {
        return isProtected;
    }

    public void setIsProtected(String isProtected) {
        this.isProtected = isProtected;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public String getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(String isRemind) {
        this.isRemind = isRemind;
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(LocalDateTime remindTime) {
        this.remindTime = remindTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(LocalDateTime modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "MemoInfo{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isProtected='" + isProtected + '\'' +
                ", background=" + background +
                ", isRemind='" + isRemind + '\'' +
                ", remindTime=" + remindTime +
                ", createdTime=" + createdTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
