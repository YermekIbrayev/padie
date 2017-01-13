package com.iskhak.padie.dao;

import java.util.Date;
import java.util.List;

import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.packagedata.SetPackageModel;
import com.iskhak.padie.model.packagedata.ViewedPackage;

public interface PackageModelDAO {
	// not using
	public List<PackageModel> list();
	// client
	public SetPackageModel setOrder(SetPackageModel order, long id);
	// provider
	public List<PackageModel> getNewOrders(String deviceId, Date date, long id);
	// provider
	public void setViewedPackage(ViewedPackage viewedPackage, long id);
	// provider
	public boolean acceptOrder(int pkgId, long id);
	// provider
	public boolean finishOrder(int pkgId, long id);
	// client
	public SetPackageModel getOrderPrice(SetPackageModel order);
	// client
	public List<PackageModel> getFinished(long cid);
	// client
	 boolean verifyOrder(int pkgId, long id);
/*	public List<SetPackageModel> testSetPackageModel()*/
}
