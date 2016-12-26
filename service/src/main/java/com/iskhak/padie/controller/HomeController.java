package com.iskhak.padie.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.iskhak.padie.dao.PackageModelDAO;
import com.iskhak.padie.dao.ServiceItemDAO;
import com.iskhak.padie.dao.UserDAO;
import com.iskhak.padie.model.listdata.GetServiceItem;
import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.security.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
     
    @Autowired
    private UserDAO userDao;
    
    @Autowired
    private ServiceItemDAO serviceItemDAO;
    
    @Autowired
    private PackageModelDAO packageModelDAO;
 
    @RequestMapping("/")
    public ModelAndView handleRequest() throws Exception {
        List<User> listUsers = userDao.list();
        ModelAndView model = new ModelAndView("UserList");
        model.addObject("userList", listUsers);
        return model;
    }
    
    @RequestMapping("/ServiceList")
    public ModelAndView getServiceList() throws Exception {
        List<GetServiceItem> serviceList = serviceItemDAO.list();
        ModelAndView model = new ModelAndView("ServiceList");
        model.addObject("serviceList", serviceList);
        return model;
    }
    
    @RequestMapping("/OrderList")
    public ModelAndView getOrderList() throws Exception {
        List<PackageModel> orderList = packageModelDAO.list();
        ModelAndView model = new ModelAndView("OrderList");
        model.addObject("orderList", orderList);
        return model;
    }
     
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView newUser() {
        ModelAndView model = new ModelAndView("UserForm");
        model.addObject("user", new User());
        return model;      
    }
     
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView editUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userDao.get(userId);
        ModelAndView model = new ModelAndView("UserForm");
        model.addObject("user", user);
        return model;      
    }
     
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("id"));
        userDao.delete(userId);
        return new ModelAndView("redirect:/");     
    }
     
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveUser(@ModelAttribute User user) {
        userDao.saveOrUpdate(user);
        return new ModelAndView("redirect:/");
    }  
}
	
