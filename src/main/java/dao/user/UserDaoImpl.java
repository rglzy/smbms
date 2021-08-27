package dao.user;

import dao.BaseDao;
import pojo.User;


import java.sql.*;
import java.util.ArrayList;

import java.util.Date;

public class UserDaoImpl implements UserDao{

    @Override
    public User getLoginUser(Connection conn, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (conn!=null){
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
            rs = BaseDao.executeQuery(conn,sql,params,rs,pstm);
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreateBy(rs.getInt("createBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
        }
        BaseDao.realease(null,rs,pstm);
        return user;
    }

    @Override
    public int updatePwd(Connection conn, int id, String userPassword) throws SQLException {
        PreparedStatement prst = null;
        int updateRows = 0;
        if (conn!=null){
            String sql = "update smbms_user set userPassword=? where id=?";
            Object[] params = {userPassword,id};
            updateRows = BaseDao.execute(conn,sql,params,prst);
        }
        BaseDao.realease(null,null,prst);
        return updateRows;
    }

    @Override
    public int queryNum(Connection conn,String queryUserName,String queryUserRole) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int num = 0;
        if (conn!=null){
            StringBuilder sql = new StringBuilder("select count(1) as num from smbms_user");
            ArrayList params = new ArrayList();
            params.add("%"+queryUserName+"%");
            sql.append(" where userName like ?");
            if (!queryUserRole.equals("")){
                params.add(queryUserRole);
                sql.append(" and userRole in (select id from smbms_role where id = ?)");
            }
            rs = BaseDao.executeQuery(conn,sql.toString(),params.toArray(),rs,prst);
            if (rs.next()){
                num = Integer.parseInt(rs.getString("num"));
            }
        }
        BaseDao.realease(null,rs,prst);
        return num;
    }

    @Override
    public ArrayList<User> getUserList(Connection conn, String queryUserName, String queryUserRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        ArrayList<User> userList = new ArrayList<>();
        if (conn!=null){
            ArrayList params = new ArrayList<>();
            StringBuilder sql = new StringBuilder("select * from smbms_user");
            params.add("%"+queryUserName+"%");
            sql.append(" where userName like ?");
            if (!queryUserRole.equals("")){
                params.add(queryUserRole);
                sql.append(" and userRole in (select id from smbms_role where id = ?)");
            }
            params.add(currentPageNo*pageSize-pageSize);
            params.add(pageSize);
            sql.append(" limit ?,?");
            //这里有一个bug
            rs = BaseDao.executeQuery(conn,sql.toString(),params.toArray(),rs,prst);
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreateBy(rs.getInt("createBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
                userList.add(user);
            }
        }
        BaseDao.realease(null,rs,prst);
        return userList;
    }

    @Override
    public User getUser(Connection conn, int id) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        User user = null;
        if (conn!=null){
            String sql = "select * from smbms_user where id = ?";
            Object[] params = {id};
            rs = BaseDao.executeQuery(conn,sql,params,rs,prst);
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreateBy(rs.getInt("createBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
        }
        BaseDao.realease(null,rs,prst);
        return user;
    }

    @Override
    public int modifyUser(Connection conn, int mid, int id, String userName, int gender, Date birthday, String phone, String address, int userRole) throws SQLException {
        PreparedStatement prst = null;
        int num = 0;
        if (conn!=null){
            String sql = "update smbms_user set userName=?,gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id=?";
            ArrayList params = new ArrayList();
            params.add(userName);
            params.add(gender);
            //因为sql.Date与util.Date存在区别，这里需要进行转型
            //这里传进来的参数是util.Date类型
            params.add(new java.sql.Date(birthday.getTime()));
            params.add(phone);
            params.add(address);
            params.add(userRole);
            params.add(mid);
            params.add(new java.sql.Timestamp(System.currentTimeMillis()));
            params.add(id);
            num = BaseDao.execute(conn,sql,params.toArray(),prst);
        }
        return num;
    }

    @Override
    public int findUser(Connection conn,String userCode) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        int num = 0;
        if (conn!=null){
            String sql = "select count(1) as num from smbms_user where userCode = ? limit 1";
            Object[] params = {userCode};
            rs = BaseDao.executeQuery(conn,sql,params,rs,prst);
            if (rs.next()){
                num = rs.getInt("num");
            }
        }
        BaseDao.realease(null,rs,prst);
        return num;

    }

    @Override
    public int addUser(Connection conn, int cid, String userCode, String userName, String userPassword, int gender, Date birthday, String phone, String address, int userRole) throws SQLException {
        PreparedStatement prst = null;
        int num = 0;
        if (conn!=null){
            String sql = "insert into smbms_user(userCode,userName,userPassword,gender,birthday,phone,address,userRole,createBy,creationDate) values(?,?,?,?,?,?,?,?,?,?)";
            ArrayList params = new ArrayList();
            params.add(userCode);
            params.add(userName);
            params.add(userPassword);
            params.add(gender);
            params.add(new java.sql.Date(birthday.getTime()));
            params.add(phone);
            params.add(address);
            params.add(userRole);
            params.add(cid);
            params.add(new java.sql.Timestamp(System.currentTimeMillis()));
            num = BaseDao.execute(conn,sql,params.toArray(),prst);
        }
        BaseDao.realease(null,null,prst);
        return num;
    }

    @Override
    public int delUser(Connection conn, int uid) throws SQLException {
        PreparedStatement prst = null;
        int ans = 0;
        //这里的状态码有三个：删除成功，删除失败，不存在该用户。我猜测是因为正常应用是软删除，所以有第三个状态码：不存在该用户
        if (conn!=null){
            String sql = "delete from smbms_user where id = ?";
            Object[] params = {uid};
            ans = BaseDao.execute(conn,sql,params,prst);
        }
        BaseDao.realease(null,null,prst);
        return ans;
    }
}
