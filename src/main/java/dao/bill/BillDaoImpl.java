package dao.bill;

import dao.BaseDao;
import pojo.Bill;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillDaoImpl implements BillDao{
    @Override
    public ArrayList<Bill> getBillList(Connection conn, String queryProductName, int queryProviderId, int queryIsPayment) throws SQLException {
        ResultSet rs = null;
        PreparedStatement prst = null;
        ArrayList<Bill> bills = new ArrayList<>();
        if (conn!=null){
            ArrayList params = new ArrayList();
            StringBuilder sql = new StringBuilder("select bill.*,provider.proName from smbms_bill as bill,smbms_provider as provider");
            params.add("%"+queryProductName+"%");
            sql.append(" where productName like ?");
            //params.add("");
            sql.append(" and provider.id=bill.providerId");
            if (queryProviderId!=0){
                params.add(queryProviderId);
                sql.append(" and providerId=?");
            }
            if (queryIsPayment!=0){
                params.add(queryIsPayment);
                sql.append(" and isPayment=?");
            }
            rs = BaseDao.executeQuery(conn,sql.toString(),params.toArray(),rs,prst);
            while (rs.next()){
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBillCode(rs.getString("billCode"));
                bill.setProductName(rs.getString("productName"));
                bill.setProductDesc(rs.getString("productDesc"));
                bill.setProductUnit(rs.getString("productUnit"));
                bill.setProductCount(rs.getBigDecimal("productCount"));
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                bill.setIsPayment(rs.getInt("isPayment"));
                bill.setCreateBy(rs.getInt("createBy"));
                bill.setCreationDate(rs.getTimestamp("creationDate"));
                bill.setProviderId(rs.getInt("providerId"));
                bill.setProviderName(rs.getString("proName"));
                bills.add(bill);
            }
        }
        BaseDao.realease(null,rs,prst);
        return bills;
    }

    @Override
    public Bill getBill(Connection conn, int billid) throws SQLException {
        ResultSet rs = null;
        PreparedStatement prst = null;
        Bill bill = new Bill();
        if (conn!=null){
            String sql = "select bill.*,provider.proName from smbms_bill as bill,smbms_provider as provider where bill.providerId=provider.id and bill.id=?";
            Object[] params = {billid};
            rs = BaseDao.executeQuery(conn,sql,params,rs,prst);
            if (rs.next()){
                bill.setId(rs.getInt("id"));
                bill.setBillCode(rs.getString("billCode"));
                bill.setProductName(rs.getString("productName"));
                bill.setProductDesc(rs.getString("productDesc"));
                bill.setProductUnit(rs.getString("productUnit"));
                bill.setProductCount(rs.getBigDecimal("productCount"));
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                bill.setIsPayment(rs.getInt("isPayment"));
                bill.setCreateBy(rs.getInt("createBy"));
                bill.setCreationDate(rs.getTimestamp("creationDate"));
                bill.setProviderId(rs.getInt("providerId"));
                bill.setProviderName(rs.getString("proName"));
            }
        }
        BaseDao.realease(null,rs,prst);
        return bill;
    }

    @Override
    public int updateBill(Connection conn, String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid, int id) throws SQLException {
        int num = 0;
        PreparedStatement prst = null;
        if (conn!=null){
            String sql = "update smbms_bill set billCode=?,productName=?,productUnit=?,productCount=?,totalPrice=?,providerId=?,isPayment=?,modifyBy=?,modifyDate=? where id=?";
            ArrayList params = new ArrayList();
            params.add(billCode);
            params.add(productName);
            params.add(productUnit);
            params.add(productCount);
            params.add(totalPrice);
            params.add(providerId);
            params.add(isPayment);
            params.add(mid);
            params.add(new java.sql.Timestamp(System.currentTimeMillis()));
            params.add(id);
            num = BaseDao.execute(conn,sql,params.toArray(),prst);
        }
        BaseDao.realease(null,null,prst);
        return num;
    }

    @Override
    public int addBill(Connection conn, String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid) throws SQLException {
        int num=0;
        PreparedStatement prst = null;
        if (conn!=null){
            String sql = "insert into smbms_bill(billCode,productName,productUnit,productCount,totalPrice,providerId,isPayment,createBy,creationDate) values (?,?,?,?,?,?,?,?,?)";
            ArrayList params = new ArrayList();
            params.add(billCode);
            params.add(productName);
            params.add(productUnit);
            params.add(productCount);
            params.add(totalPrice);
            params.add(providerId);
            params.add(isPayment);
            params.add(mid);
            params.add(new java.sql.Timestamp(System.currentTimeMillis()));
            num = BaseDao.execute(conn,sql,params.toArray(),prst);
        }
        BaseDao.realease(null,null,prst);
        return num;
    }

    @Override
    public int delBill(Connection conn, int billid) throws SQLException {
        PreparedStatement prst = null;
        int num = 0;
        if (conn!=null){
            String sql = "delete from smbms_bill where id = ?";
            Object[] params = {billid};
            num = BaseDao.execute(conn,sql,params,prst);
        }
        BaseDao.realease(null,null,prst);
        return num;
    }
}
