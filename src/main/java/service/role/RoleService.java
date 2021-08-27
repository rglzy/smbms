package service.role;

import pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoleService {
    public ArrayList<Role> getRoleList();

}
