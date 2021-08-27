package dao.role;

import dao.BaseDao;
import pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleDaoImpl implements RoleDao{
    @Override
    public ArrayList<Role> getRoleList(Connection conn) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        ArrayList roleList = new ArrayList();
        if (conn!=null){
            String sql = "select * from smbms_role";
            rs = BaseDao.executeQuery(conn,sql,new Object[0],rs,prst);
            while(rs.next()){
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleCode(rs.getString("roleCode"));
                role.setRoleName(rs.getString("roleName"));
                role.setCreateBy(rs.getInt("createBy"));
                role.setCreationDate(rs.getTimestamp("creationDate"));
                role.setModifyBy(rs.getInt("modifyBy"));
                role.setModifyDate(rs.getTimestamp("modifyDate"));
                roleList.add(role);
            }
        }
        BaseDao.realease(null,rs,prst);
        return roleList;
    }
}
