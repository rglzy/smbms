package servlet.bill;

import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.Bill;
import pojo.Provider;
import pojo.User;
import service.bill.BillService;
import service.bill.BillServiceImpl;
import service.provider.ProviderService;
import service.provider.ProviderServiceImpl;
import util.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("query")){
            query(req,resp);
        }else if (method.equals("view")){
            view(req,resp);
        }else if (method.equals("modify")){
            modify(req,resp);
        }else if (method.equals("getproviderlist")){
            getproviderlist(req,resp);
        }else if (method.equals("modifysave")){
            modifysave(req,resp);
        }else if (method.equals("add")){
            add(req,resp);
        }else if (method.equals("delbill")){
            delbill(req,resp);
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillServiceImpl();
        ProviderService providerService = new ProviderServiceImpl();
        String queryProductName = req.getParameter("queryProductName");
        if (queryProductName==null){
            queryProductName = "";
        }
        int queryProviderId = 0;
        if (req.getParameter("queryProviderId")!=null){
            queryProviderId = Integer.parseInt(req.getParameter("queryProviderId"));
        }
        int queryIsPayment = 0;
        if (req.getParameter("queryIsPayment")!=null){
            queryIsPayment = Integer.parseInt(req.getParameter("queryIsPayment"));
        }
        ArrayList bills = billService.getBillList(queryProductName,queryProviderId,queryIsPayment);
        ArrayList providers = providerService.getListForBill();
        req.setAttribute("providerList",providers);
        req.setAttribute("billList",bills);
        req.setAttribute("queryProductName",queryProductName);
        req.setAttribute("queryProviderId",queryProviderId);
        req.setAttribute("queryIsPayment",queryIsPayment);
        try {
            req.getRequestDispatcher("billlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void view(HttpServletRequest req, HttpServletResponse resp) {
        int billid = Integer.parseInt(req.getParameter("billid"));
        BillService billService = new BillServiceImpl();
        Bill bill = billService.getBill(billid);
        req.setAttribute("bill",bill);
        try {
            req.getRequestDispatcher("billview.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp) {
        int billid = Integer.parseInt(req.getParameter("billid"));
        BillService billService = new BillServiceImpl();
        Bill bill = billService.getBill(billid);
        req.setAttribute("bill",bill);
        try {
            req.getRequestDispatcher("billmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getproviderlist(HttpServletRequest req, HttpServletResponse resp) {
        ProviderService providerService = new ProviderServiceImpl();
        ArrayList providers = providerService.getListForBill();
        resp.setContentType("application/json");
        try {
            PrintWriter out = resp.getWriter();
            out.write(JSONArray.toJSONString(providers));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifysave(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillServiceImpl();
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        BigDecimal productCount = new BigDecimal(req.getParameter("productCount"));
        BigDecimal totalPrice = new BigDecimal(req.getParameter("totalPrice"));
        int providerId = Integer.parseInt(req.getParameter("providerId"));
        int isPayment = Integer.parseInt(req.getParameter("isPayment"));
        int mid = ((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        int id = Integer.parseInt(req.getParameter("id"));
        boolean flag = billService.updateBill(billCode,productName,productUnit,productCount,totalPrice,providerId,isPayment,mid,id);
        if (flag){
            try {
                resp.sendRedirect("bill.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillServiceImpl();
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        BigDecimal productCount = new BigDecimal(req.getParameter("productCount"));
        BigDecimal totalPrice = new BigDecimal(req.getParameter("totalPrice"));
        int providerId = Integer.parseInt(req.getParameter("providerId"));
        int isPayment = Integer.parseInt(req.getParameter("isPayment"));
        int mid = ((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        boolean flag = false;
        flag = billService.addBill(billCode,productName,productUnit,productCount,totalPrice,providerId,isPayment,mid);
        if (flag){
            try {
                resp.sendRedirect("bill.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void delbill(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillServiceImpl();
        int billid = Integer.parseInt(req.getParameter("billid"));
        boolean flag = billService.delBill(billid);
        HashMap map = new HashMap();
        if (flag){
            map.put("delResult","true");
        }else {
            map.put("delResult","false");
        }
        try {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.write(JSONArray.toJSONString(map));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
