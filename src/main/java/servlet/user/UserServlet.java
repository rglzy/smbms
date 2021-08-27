package servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.xdevapi.JsonArray;
import dao.user.UserDao;
import dao.user.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pojo.Role;
import pojo.User;
import service.role.RoleService;
import service.role.RoleServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import util.Constants;
import util.PageSupport;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")){
            savePwd(req,resp);
        }else if (method.equals("pwdmodify")){
            pwdModify(req,resp);
        }else if (method.equals("query")){
            query(req,resp);
        }else if (method.equals("view")){
            view(req,resp);
        }else if (method.equals("modify")){
            modify(req,resp);
        }else if (method.equals("modifyexe")){
            modifyexe(req,resp);
        }else if (method.equals("getrolelist")){
            getrolelist(req,resp);
        }else if (method.equals("add")){
            add(req,resp);
        }else if (method.equals("ucexist")){
            ucexist(req,resp);
        }else if (method.equals("deluser")){
            delUser(req,resp);
        }else if (method.equals("flushpage")){
            flushpage(req,resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void savePwd(HttpServletRequest req,HttpServletResponse resp){
        User user = (User)req.getSession().getAttribute(Constants.USER_SESSION);
        String newPassword = req.getParameter("newpassword");
        UserService userService = new UserServiceImpl();
        boolean flag = false;
        if (!newPassword.equals("")){
            flag = userService.updatePwd(user.getId(),newPassword);
        }
        if (flag){
            req.getSession().removeAttribute(Constants.USER_SESSION);

        }else{
            //这里用的是请求转发的方法，通过这种方式不会丢失参数
            req.setAttribute(Constants.SYS_MESSAGE,"密码修改失败，请重试");
        }
        try {
            //这里用的是请求转发，所以啥都没变化
            //filter无法过滤请求转发
            //如果要用请求转发的话必须把请求内的参数也一并去掉
            resp.sendRedirect("pwdmodify.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pwdModify(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User)req.getSession().getAttribute(Constants.USER_SESSION);
        String oldPassword = req.getParameter("oldpassword");
        Map<String,String> map = new HashMap<>();
        if (user==null){
            map.put("result","sessionerror");
        }else if (oldPassword.equals("")){
            map.put("result","error");
        }else if (!oldPassword.equals(user.getUserPassword())){
            map.put("result","false");
        }else if (oldPassword.equals(user.getUserPassword())){
            map.put("result","true");
        }
        //这里是将返回的数据以json格式返回
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

    private void query(HttpServletRequest req, HttpServletResponse resp) {
        String queryUserName = req.getParameter("queryUserName");
        String queryUserRole = req.getParameter("queryUserRole");
        String tempPageNo = req.getParameter("pageIndex");
        Integer currentPageNo = 1;
        if (queryUserName==null){
            queryUserName = "";
        }
        if (queryUserRole==null||queryUserRole.equals("0")){
            queryUserRole = "";
        }
        if (tempPageNo!=null){
            currentPageNo = Integer.parseInt(tempPageNo);
        }
        RoleService roleService = new RoleServiceImpl();
        UserService userService = new UserServiceImpl();
        //获得总记录数
        int totalCount = userService.queryNum(queryUserName,queryUserRole);
        PageSupport pageSupport = new PageSupport(totalCount);
        //优化currentPageNo的设置
        if (currentPageNo<=0){
            currentPageNo=1;
        }
        if (currentPageNo>pageSupport.getTotalPageCount()){
            currentPageNo=pageSupport.getTotalPageCount();
        }
        //获得用户列表
        userService.getUserList(queryUserName,queryUserRole,currentPageNo,Constants.PAGE_SIZE);
        Object[] userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo,Constants.PAGE_SIZE).toArray();
        //获得角色列表
        Object[] roleList = roleService.getRoleList().toArray();

        //设置参数
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);
        req.setAttribute("userList",userList);
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("totalPageCount",pageSupport.getTotalPageCount());
        req.setAttribute("currentPageNo",currentPageNo);
        //req.setAttribute("currentPageNo",pageSupport.getCurrentPageNo());
        //这里理论上应该用来获得currentPageNo，但是有必要嘛？
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void view(HttpServletRequest req, HttpServletResponse resp) {
        int uid = Integer.parseInt(req.getParameter("uid"));
        UserService userService = new UserServiceImpl();
        User user = userService.getUser(uid);
        req.setAttribute("user",user);
        try {
            req.getRequestDispatcher("userview.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp) {
        String uid = req.getParameter("uid");
        UserService userService = new UserServiceImpl();
        User user = userService.getUser(Integer.parseInt(uid));
        req.setAttribute("user",user);
        try {
            req.getRequestDispatcher("usermodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getrolelist(HttpServletRequest req, HttpServletResponse resp) {
        RoleService roleService = new RoleServiceImpl();
        ArrayList<Role> roleList = roleService.getRoleList();
        try {
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            out.write(JSONArray.toJSONString(roleList));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void modifyexe(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserServiceImpl();
        int mid = ((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        int id = Integer.parseInt(req.getParameter("uid"));
        String userName = req.getParameter("userName");
        int gender = Integer.parseInt(req.getParameter("gender"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = sdf.parse(req.getParameter("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int userRole = Integer.parseInt(req.getParameter("userRole"));
        userService.modifyUser(mid,id,userName,gender,birthday,phone,address,userRole);
//        User user = userService.getUser(id);
//        req.removeAttribute("uid");
//        req.removeAttribute("userName");
//        req.removeAttribute("gender");
//        req.removeAttribute("birthday");
//        req.removeAttribute("phone");
//        req.removeAttribute("address");
//        req.removeAttribute("userRole");
//        req.setAttribute("method","query");
        //这里本来想的是停留在原来的页面，但是发现返回按钮写的逻辑是
        //返回上一页，即修改前的页面显示，所以就改成重定向了
        //搞清楚重定向和请求转发的区别也许就可以改成本来想要的样子

        try {
            resp.sendRedirect("user.do?method=query");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserServiceImpl();
        int cid = ((User)req.getSession().getAttribute(Constants.USER_SESSION)).getId();
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        int gender = Integer.parseInt(req.getParameter("gender"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        try {
            birthday = sdf.parse(req.getParameter("birthday"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int userRole = Integer.parseInt(req.getParameter("userRole"));
        userService.addUser(cid,userCode,userName,userPassword,gender,birthday,phone,address,userRole);
        try {
            resp.sendRedirect("user.do?method=query");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ucexist(HttpServletRequest req, HttpServletResponse resp) {
        String userCode = req.getParameter("userCode");
        UserService userService = new UserServiceImpl();
        boolean result = userService.findUser(userCode);
        try {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            Map<String,String> map = new HashMap<>();
            if (result){
                map.put("userCode","exist");
            }else{
                map.put("userCode","notExist");
            }
            out.write(JSONArray.toJSONString(map));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delUser(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserServiceImpl();
        int uid = Integer.parseInt(req.getParameter("uid"));
        boolean result = userService.delUser(uid);
        try {
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            Map<String,String> map = new HashMap<>();
            if (result){
                map.put("delResult","true");
            }else{
                map.put("delResult","false");
            }
            out.write(JSONArray.toJSONString(map));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flushpage(HttpServletRequest req, HttpServletResponse resp) {
        String queryUserName = req.getParameter("queryUserName");
        String queryUserRole = req.getParameter("queryUserRole");
        String tempPageNo = req.getParameter("pageIndex");
        Integer currentPageNo = 1;
        if (queryUserName==null){
            queryUserName = "";
        }
        if (queryUserRole==null||queryUserRole.equals("0")){
            queryUserRole = "";
        }
        if (tempPageNo!=null){
            currentPageNo = Integer.parseInt(tempPageNo);
        }
        RoleService roleService = new RoleServiceImpl();
        UserService userService = new UserServiceImpl();
        //获得总记录数
        int totalCount = userService.queryNum(queryUserName,queryUserRole);
        PageSupport pageSupport = new PageSupport(totalCount);
        //优化currentPageNo的设置
        if (currentPageNo<=0){
            currentPageNo=1;
        }
        if (currentPageNo>pageSupport.getTotalPageCount()){
            currentPageNo=pageSupport.getTotalPageCount();
        }
        //获得用户列表
        userService.getUserList(queryUserName,queryUserRole,currentPageNo,Constants.PAGE_SIZE);
        Object[] userList = userService.getUserList(queryUserName,queryUserRole,currentPageNo,Constants.PAGE_SIZE).toArray();
        //获得角色列表
        Object[] roleList = roleService.getRoleList().toArray();
        HashMap map = new HashMap();
        map.put("queryUserName",queryUserName);
        map.put("queryUserRole",queryUserRole);
        map.put("roleList",roleList);
        map.put("userList",userList);
        map.put("totalPageCount",pageSupport.getTotalPageCount());
        map.put("totalCount",totalCount);
        map.put("currentPageNo",currentPageNo);
        map.put("result",true);
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
