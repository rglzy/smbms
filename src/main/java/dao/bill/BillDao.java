package dao.bill;

import pojo.Bill;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BillDao {
    public ArrayList<Bill> getBillList(Connection conn,String queryProductName,int queryProviderId,int queryIsPayment) throws SQLException;

    public Bill getBill(Connection conn,int billid) throws SQLException;

    public int updateBill(Connection conn, String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid, int id) throws SQLException;

    public int addBill(Connection conn, String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid) throws SQLException;

    public int delBill(Connection conn,int billid) throws SQLException;
}
