package com.iskhak.padie.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iskhak.padie.dao.PackageModelDAO;
import com.iskhak.padie.dao.SelectedAddDAO;
import com.iskhak.padie.dao.ServiceItemDAO;
import com.iskhak.padie.model.listdata.GetServiceItem;
import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.packagedata.SelectedItemsAdd;
import com.iskhak.padie.model.packagedata.SetPackageModel;
import com.iskhak.padie.model.packagedata.ViewedPackage;

@RestController
@RequestMapping(value="json")
public class JSONController {
	@Autowired
	private ServiceItemDAO serviceItemDAO;
	@Autowired
	private PackageModelDAO packageModelDAO;
	@Autowired
	private SelectedAddDAO selectedAddDAO;
	
	@RequestMapping(value="/serviceList", method = RequestMethod.GET, produces = "application/json")
	public List<GetServiceItem> getServiceListJSON(){
		return serviceItemDAO.list();
	}
	
	@RequestMapping(value="/OrderList", method = RequestMethod.GET, produces = "application/json")
	public List<PackageModel> getOrderListJSON(){
		return packageModelDAO.list();
	}
	
	@RequestMapping(value="/OrderList1", method = RequestMethod.GET, produces = "application/json")
	public List<SelectedItemsAdd> getOrderList1JSON(){
		return selectedAddDAO.list();
	}
	
	@RequestMapping(value="/orderService", method = RequestMethod.POST)
	public @ResponseBody SetPackageModel getOrderSum(@RequestBody SetPackageModel order){
		System.out.println(order.getId());
		return packageModelDAO.getOrder(order);
	}
	
/*	@RequestMapping(value="/testSetPackageList", method = RequestMethod.GET, produces = "application/json")
	public List<SetPackageModel> testSetPackageList(){
		return packageModelDAO.testSetPackageModel();
	}*/
	
	@RequestMapping(value="/getNewOrders/{deviceId}/{date}", method = RequestMethod.GET, produces = "application/json")
	public List<PackageModel> getNewOrders(@PathVariable("deviceId") String deviceId, @PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd-HH-mm-ss") Date date){
		return packageModelDAO.getNewOrders(deviceId, date);
	}
	
	@RequestMapping(value="/setViewedOrders", method = RequestMethod.POST)
	public void viewedOrder( @RequestBody ViewedPackage viewedPackage){
		System.out.println("Viewed package:"+viewedPackage.getId()+" viewed"+viewedPackage.getViewed());
		packageModelDAO.setViewedPackage(viewedPackage);
	}
}
