package dao.provider;

import pojo.Provider;
import util.Constants;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProviderDao {
    public ArrayList<Provider> getProviderList(Connection conn, String queryProCode, String queryProName) throws SQLException;

    public Provider getProvider(Connection conn,int proid) throws SQLException;

    public int updateProvider(Connection conn,String proCode,String proName,String proContact,String proPhone,String proAddress,String proFax,String proDesc,int pid,int mid)throws SQLException;

    public int findProvider(Connection conn,String proCode) throws SQLException;

    public int addProvider(Connection conn,String proCode,String proName,String proContact,String proPhone,String proAddress,String proFax,String proDesc,int mid) throws SQLException;

    public int delProvider(Connection conn,int proId) throws SQLException;

    public ArrayList<Provider> getListForBill(Connection conn) throws SQLException;
}
