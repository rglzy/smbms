package service.bill;

import dao.BaseDao;
import dao.bill.BillDao;
import dao.bill.BillDaoImpl;
import pojo.Bill;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillServiceImpl implements BillService{
    private BillDao billDao;
    public BillServiceImpl(){
        billDao = new BillDaoImpl();
    }

    @Override
    public ArrayList<Bill> getBillList(String queryProductName, int queryProviderId, int queryIsPayment) {
        Connection conn = BaseDao.getConnection();
        ArrayList<Bill> bills = null;
        try {
            bills = billDao.getBillList(conn,queryProductName,queryProviderId,queryIsPayment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return bills;
    }

    @Override
    public Bill getBill(int billid) {
        Connection conn = BaseDao.getConnection();
        Bill bill = new Bill();
        try {
            bill = billDao.getBill(conn,billid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return bill;
    }

    @Override
    public boolean updateBill(String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid, int id) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int num = billDao.updateBill(conn,billCode,productName,productUnit,productCount,totalPrice,providerId,isPayment,mid,id);
            if (num>0)
                flag = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return flag;
    }

    @Override
    public boolean addBill(String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int num = billDao.addBill(conn,billCode,productName,productUnit,productCount,totalPrice,providerId,isPayment,mid);
            if (num>0)
                flag = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return flag;
    }

    @Override
    public boolean delBill(int billid) {
        Connection conn = BaseDao.getConnection();
        boolean flag = false;
        try {
            int num = billDao.delBill(conn,billid);
            if (num>0)
                flag=true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        BaseDao.realease(conn,null,null);
        return flag;
    }


}
