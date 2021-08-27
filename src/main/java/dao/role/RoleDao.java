package dao.role;

import pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoleDao {
    public ArrayList<Role> getRoleList(Connection conn) throws SQLException;
}
