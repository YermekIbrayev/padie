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
	public SetPackageModel setOrder(SetPackageModel order);
	// provider
	public List<PackageModel> getNewOrders(String deviceId, Date date);
	// provider
	public void setViewedPackage(ViewedPackage viewedPackage);
	// provider
	public boolean acceptOrder(int pkgId);
	// client
	public SetPackageModel getOrderPrice(SetPackageModel order);
/*	public List<SetPackageModel> testSetPackageModel()*/
}
