package servlet.provider;

import com.alibaba.fastjson.JSONArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.Provider;
import pojo.User;
import service.provider.ProviderService;
import service.provider.ProviderServiceImpl;
import util.Constants;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProviderServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("query")){
            query(req,resp);
        }else if (method.equals("view")){
            view(req,resp);
        }else if (method.equals("modify")){
            modify(req,resp);
        }else if (method.equals("modifyexe")){
            modifyexe(req,resp);
        }else if (method.equals("add")){
            add(req,resp);
        }else if (method.equals("delprovider")){
            delprovider(req,resp);
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) {
        String queryProCode = req.getParameter("queryProCode");
        String queryProName = req.getParameter("queryProName");
        if (queryProCode==null){
            queryProCode="";
        }
        if (queryProName==null){
            queryProName="";
        }
        ProviderService providerService = new ProviderServiceImpl();
        Object[] providerList = providerService.getProviderList(queryProCode,queryProName).toArray();
        req.setAttribute("providerList",providerList);
        req.setAttribute("queryProCode",queryProCode);
        req.setAttribute("queryProName",queryProName);
        try {
            req.getRequestDispatcher("providerlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void view(HttpServletRequest req, HttpServletResponse resp) {
        String proid = req.getParameter("proid");
        ProviderService providerService = new ProviderServiceImpl();
        Provider provider = providerService.getProvider(Integer.parseInt(proid));
        req.setAttribute("provider",provider);
        try {
            req.getRequestDispatcher("providerview.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp) {
        String proid = req.getParameter("proid");
        ProviderService providerService = new ProviderServiceImpl();
        Provider provider = providerService.getProvider(Integer.parseInt(proid));
        req.setAttribute("provider",provider);
        try {
            req.getRequestDispatcher("providermodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyexe(HttpServletRequest req, HttpServletResponse resp) {
        int mid = ((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");
        int pid = Integer.parseInt(req.getParameter("pid"));
        ProviderService providerService = new ProviderServiceImpl();
        boolean result = providerService.modifyProvider(proCode,proName,proContact,proPhone,proAddress,proFax,proDesc,pid,mid);
        if (result){
            try {
                req.getRequestDispatcher("provider.do?method=query").forward(req,resp);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }


    private void add(HttpServletRequest req, HttpServletResponse resp) {
        int mid = ((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        String proCode = req.getParameter("proCode");
        String proName = req.getParameter("proName");
        String proContact = req.getParameter("proContact");
        String proPhone = req.getParameter("proPhone");
        String proAddress = req.getParameter("proAddress");
        String proFax = req.getParameter("proFax");
        String proDesc = req.getParameter("proDesc");
        ProviderService providerService = new ProviderServiceImpl();
        boolean flag = providerService.addProvider(proCode,proName,proContact,proPhone,proAddress,proFax,proDesc,mid);
        if (flag){
            try {
                req.getRequestDispatcher("provider.do?method=query").forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void delprovider(HttpServletRequest req, HttpServletResponse resp) {
        int proId = Integer.parseInt(req.getParameter("proid"));
        ProviderService providerService = new ProviderServiceImpl();
        boolean flag = providerService.delProvider(proId);
        //这里不用做请求转发，因为jsp里面并没有做分页
        Map map = new HashMap<>();
        if (flag){
            map.put("delResult","true");
        }else{
            map.put("delResult","false");
        }
        resp.setContentType("application/json");
        try {
            PrintWriter out = resp.getWriter();
            out.write(JSONArray.toJSONString(map));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
