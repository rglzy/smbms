package service.provider;

import dao.BaseDao;
import dao.provider.ProviderDao;
import dao.provider.ProviderDaoImpl;
import pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProviderServiceImpl implements ProviderService{
    private ProviderDao providerDao;

    public ProviderServiceImpl(){
        this.providerDao = new ProviderDaoImpl();
    }

    @Override
    public ArrayList<Provider> getProviderList(String queryProCode, String queryProName) {
        Connection conn = BaseDao.getConnection();
        ArrayList<Provider> providerList = null;
        try {
            providerList = providerDao.getProviderList(conn,queryProCode,queryProName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        return providerList;
    }

    @Override
    public Provider getProvider(int proid) {
        Connection conn = BaseDao.getConnection();
        Provider provider = null;
        try {
            provider = providerDao.getProvider(conn,proid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.realease(conn,null,null);
        }
        return provider;
    }

    @Override
    public boolean modifyProvider(String proCode, String proName, String proContact, String proPhone, String proAddress, String proFax, String proDesc, int pid, int mid) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int num = providerDao.updateProvider(conn,proCode,proName,proContact,proPhone,proAddress,proFax,proDesc,pid,mid);
            if (num>0)
                flag=true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return flag;
    }

    @Override
    public boolean findProvider(String proCode) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int num = providerDao.findProvider(conn,proCode);
            if (num>0)
                flag = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return  flag;
    }

    @Override
    public boolean addProvider(String proCode, String proName, String proContact, String proPhone, String proAddress, String proFax, String proDesc, int mid) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int num = providerDao.addProvider(conn,proCode,proName,proContact,proPhone,proAddress,proFax,proDesc,mid);
            if (num>0)
                flag = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return flag;
    }

    @Override
    public boolean delProvider(int proId) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int num = providerDao.delProvider(conn,proId);
            if (num>0){
                flag = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return flag;
    }

    @Override
    public ArrayList<Provider> getListForBill() {
        Connection conn = BaseDao.getConnection();
        ArrayList<Provider> providers = null;
        try {
            providers = providerDao.getListForBill(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return providers;
    }
}
