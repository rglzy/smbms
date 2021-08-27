package service.user;

import pojo.User;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.Date;


public interface UserService {
    public User login(String userCode, String userPassword);

    public boolean updatePwd(int id, String userPassword);

    public int queryNum(String queryUserName,String queryUserRole);

    public ArrayList<User> getUserList(String queryUserName, String queryUserRole, int currentPageNo, int pageSize);

    public User getUser(int id);

    public boolean modifyUser(int mid, int id, String userName, int gender, Date birthday, String phone, String address, int userRole);

    public boolean findUser(String userCode);

    public boolean addUser(int cid, String userCode, String userName, String userPassword, int gender, Date birthday, String phone, String address, int userRole);

    public boolean delUser(int uid);
}
