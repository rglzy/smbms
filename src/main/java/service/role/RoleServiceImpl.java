package service.role;

import dao.BaseDao;
import dao.role.RoleDao;
import dao.role.RoleDaoImpl;
import org.junit.jupiter.api.Test;
import pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleServiceImpl implements RoleService{

    private RoleDao roleDao;
    public RoleServiceImpl(){
        roleDao = new RoleDaoImpl();
    }

    @Override
    public ArrayList<Role> getRoleList() {
        Connection conn = BaseDao.getConnection();
        ArrayList<Role> ans = null;
        try {
            ans = roleDao.getRoleList(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        return ans;
    }

}
