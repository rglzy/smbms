package service.bill;

import pojo.Bill;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface BillService {
    public ArrayList<Bill> getBillList(String queryProductName,int queryProviderId,int queryIsPayment);

    public Bill getBill(int billid);

    public boolean updateBill(String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid, int id);

    public boolean addBill(String billCode, String productName, String productUnit, BigDecimal productCount, BigDecimal totalPrice, int providerId, int isPayment, int mid);

    public boolean delBill(int billid);
}
