package service.provider;

import pojo.Provider;

import java.util.ArrayList;

public interface ProviderService {
    public ArrayList<Provider> getProviderList(String queryProCode,String queryProName);

    public Provider getProvider(int proid);

    public boolean modifyProvider(String proCode, String proName, String proContact, String proPhone, String proAddress, String proFax, String proDesc, int pid, int mid);

    public boolean findProvider(String proCode);

    public boolean addProvider(String proCode,String proName,String proContact,String proPhone,String proAddress,String proFax,String proDesc,int mid);

    public boolean delProvider(int proId);

    public ArrayList<Provider> getListForBill();
}
