package com.iskhak.padie.dao;

import java.util.Date;
import java.util.List;

import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.packagedata.SetPackageModel;
import com.iskhak.padie.model.packagedata.ViewedPackage;

public interface PackageModelDAO {
	public List<PackageModel> list();
	public SetPackageModel setOrder(SetPackageModel order);
	public List<PackageModel> getNewOrders(String deviceId, Date date);
	public void setViewedPackage(ViewedPackage viewedPackage);
	public boolean acceptOrder(int pkgId);
	public SetPackageModel getOrderPrice(SetPackageModel order);
/*	public List<SetPackageModel> testSetPackageModel()*/
}
