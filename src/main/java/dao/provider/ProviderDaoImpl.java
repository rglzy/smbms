package dao.provider;

import dao.BaseDao;
import pojo.Provider;

import java.sql.*;
import java.util.ArrayList;

public class ProviderDaoImpl implements ProviderDao{

    @Override
    public ArrayList<Provider> getProviderList(Connection conn, String queryProCode, String queryProName) throws SQLException {
        PreparedStatement prst = null;
        ResultSet rs = null;
        ArrayList<Provider> provderList = new ArrayList<>();
        if (conn!=null){
            StringBuilder sql = new StringBuilder("select * from smbms_provider");
            ArrayList params = new ArrayList();
            params.add("%"+queryProCode+"%");
            sql.append(" where proCode like ?");
            if (!queryProName.equals("")){
                params.add("%"+queryProName+"%");
                sql.append(" and proName like ?");
            }
            rs = BaseDao.executeQuery(conn,sql.toString(),params.toArray(),rs,prst);
            while(rs.next()){
                Provider provider = new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreateBy(rs.getInt("createBy"));
                provider.setCreationDate(rs.getTimestamp("creationDate"));
                provderList.add(provider);
            }
        }
        BaseDao.realease(null,rs,prst);
        return provderList;
    }

    @Override
    public Provider getProvider(Connection conn, int proid) throws SQLException {
        ResultSet rs = null;
        PreparedStatement prst = null;
        Provider provider = null;
        if (conn!=null){
            String sql = "select * from smbms_provider where id = ?";
            Object[] params = {proid};
            rs = BaseDao.executeQuery(conn,sql,params,rs,prst);
            if (rs.next()){
                provider = new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreateBy(rs.getInt("createBy"));
                provider.setCreationDate(rs.getTimestamp("creationDate"));
            }
        }
        BaseDao.realease(null,rs,prst);
        return provider;
    }

    @Override
    public int updateProvider(Connection conn, String proCode, String proName, String proContact, String proPhone, String proAddress, String proFax, String proDesc, int pid, int mid) throws SQLException {
        PreparedStatement prst = null;
        int num = 0;
        if (conn!=null){
            String sql = "update smbms_provider set proCode=?,proName=?,proContact=?,proPhone=?,proAddress=?,proFax=?,proDesc=?,modifyBy=?,modifyDate=? where id=?";
            ArrayList params = new ArrayList();
            params.add(proCode);
            params.add(proName);
            params.add(proContact);
            params.add(proPhone);
            params.add(proAddress);
            params.add(proFax);
            params.add(proDesc);
            params.add(mid);
            params.add(new java.sql.Timestamp(System.currentTimeMillis()));
            params.add(pid);
            num = BaseDao.execute(conn,sql,params.toArray(),prst);
        }
        BaseDao.realease(null,null,prst);
        return num;
    }

    @Override
    public int findProvider(Connection conn, String proCode) throws SQLException {
        ResultSet rs = null;
        PreparedStatement prst = null;
        int num = 0;
        if (conn!=null){
            String sql = "select count(1) as num from smbms_provider where proCode = ? limit 1";
            Object[] params = {proCode};
            rs = BaseDao.executeQuery(conn,sql,params,rs,prst);
            num = rs.getInt("num");
        }
        BaseDao.realease(null,rs,prst);
        return num;
    }

    @Override
    public int addProvider(Connection conn, String proCode, String proName, String proContact, String proPhone, String proAddress, String proFax, String proDesc, int mid) throws SQLException {
        PreparedStatement prst = null;
        int num = 0;
        if (conn!=null){
            String sql = "insert into smbms_provider(proCode,proName,proContact,proPhone,proAddress,proFax,proDesc,createBy,creationDate) values (?,?,?,?,?,?,?,?,?)";
            ArrayList params = new ArrayList();
            params.add(proCode);
            params.add(proName);
            params.add(proContact);
            params.add(proPhone);
            params.add(proAddress);
            params.add(proFax);
            params.add(proDesc);
            params.add(mid);
            params.add(new java.sql.Timestamp(System.currentTimeMillis()));
            num = BaseDao.execute(conn,sql,params.toArray(),prst);
        }
        BaseDao.realease(null,null,prst);
        return num;
    }

    @Override
    public int delProvider(Connection conn, int proId) throws SQLException {
        PreparedStatement prst = null;
        int num = 0;
        if (conn!=null){
            String sql = "delete from smbms_provider where id = ?";
            Object[] params = {proId};
            num = BaseDao.execute(conn,sql,params,prst);
        }
        BaseDao.realease(null,null,prst);
        return num;
    }

    @Override
    public ArrayList<Provider> getListForBill(Connection conn) throws SQLException {
        ResultSet rs = null;
        PreparedStatement prst = null;
        ArrayList<Provider> providers = new ArrayList<>();
        if (conn!=null){
            String sql = "select * from smbms_provider";
            Object[] params = {};
            rs = BaseDao.executeQuery(conn,sql,params,rs,prst);
            while(rs.next()){
                Provider provider = new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreateBy(rs.getInt("createBy"));
                provider.setCreationDate(rs.getTimestamp("creationDate"));
                providers.add(provider);
            }
        }
        BaseDao.realease(null,rs,prst);
        return providers;
    }
}
