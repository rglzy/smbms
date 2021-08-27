package dao.user;

import pojo.User;
import util.Constants;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public interface UserDao {

    //得到要登陆的用户，这里只是得到用户的信息，不进行信息比对
    public User getLoginUser(Connection conn, String userCode) throws SQLException;

    //修改用户的密码
    public int updatePwd(Connection conn, int id, String userPassword) throws SQLException;

    //查询拥有的所有记录数，即用户的个数
    public int queryNum(Connection conn,String queryUserName,String queryUserRole) throws SQLException;

    //返回当前分页下应该有的数据数目，即用户列表
    public ArrayList<User> getUserList(Connection conn,String queryUserName,String queryUserRole,int currentPageNo,int pageSize) throws SQLException;

    //获得user的详细信息
    public User getUser(Connection conn,int id) throws SQLException;

    //更改user的详细信息
    public int modifyUser(Connection conn, int mid, int id, String userName, int gender, Date birthday, String phone, String address, int userRole) throws SQLException;

    //检查新加user的userCode是否已经存在
    public int findUser(Connection conn,String userCode) throws SQLException;

    //添加user
    public int addUser(Connection conn,int cid,String userCode,String userName,String userPassword,int gender,Date birthday,String phone,String address,int userRole) throws SQLException;

    //删除用户
    public int delUser(Connection conn,int uid) throws SQLException;
}
