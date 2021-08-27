package service.user;

import dao.BaseDao;
import dao.user.UserDao;
import dao.user.UserDaoImpl;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import pojo.User;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;


public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String userPassword) {
        User user = null;
        Connection conn = BaseDao.getConnection();
        try {
            user = userDao.getLoginUser(conn,userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        if (user!=null){
            if (!userPassword.equals(user.getUserPassword())){
                user = null;
            }
        }
        return user;
    }

    @Override
    public boolean updatePwd(int id, String userPassword) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int updateRows = userDao.updatePwd(conn,id,userPassword);
            if (updateRows>0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
          BaseDao.realease(conn,null,null);
        }
        return flag;
    }

    @Override
    public int queryNum(String queryUserName,String queryUserRole) {
        Connection conn = BaseDao.getConnection();
        int num = 0;
        try {
            num = userDao.queryNum(conn,queryUserName,queryUserRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        return num;
    }

    @Override
    public ArrayList<User> getUserList(String queryUserName, String queryUserRole, int currentPageNo, int pageSize) {
        Connection conn = BaseDao.getConnection();
        ArrayList<User> userList = null;
        try {
            userList = userDao.getUserList(conn,queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        return userList;
    }

    @Override
    public User getUser(int id) {
        Connection conn = BaseDao.getConnection();
        User user = null;
        try {
            user = userDao.getUser(conn,id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        return user;
    }

    @Override
    public boolean modifyUser(int mid, int id, String userName, int gender, Date birthday, String phone, String address, int userRole) {
        Connection conn = BaseDao.getConnection();
        int ans = 0;
        boolean flag = false;
        try {
            ans = userDao.modifyUser(conn,mid,id,userName,gender,birthday,phone,address,userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        if (ans>0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean findUser(String userCode) {
        Connection conn = BaseDao.getConnection();
        int ans=0;
        boolean flag = false;
        try {
            ans = userDao.findUser(conn,userCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        if (ans>0){
            flag=true;
        }
        return  flag;
    }

    @Override
    public boolean addUser(int cid, String userCode, String userName, String userPassword, int gender, Date birthday, String phone, String address, int userRole) {
        Connection conn = BaseDao.getConnection();
        int ans = 0;
        boolean flag = false;
        try {
            ans = userDao.addUser(conn,cid,userCode,userName,userPassword,gender,birthday,phone,address,userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        if (ans>0){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean delUser(int uid) {
        Connection conn = BaseDao.getConnection();
        int ans = 0;
        boolean flag = false;
        try {
            ans = userDao.delUser(conn,uid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        if (ans>0){
            flag=true;
        }
        return flag;
    }

}
