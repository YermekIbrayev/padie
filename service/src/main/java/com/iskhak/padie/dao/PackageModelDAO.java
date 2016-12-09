package com.iskhak.padie.dao;

import java.util.Date;
import java.util.List;

import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.packagedata.SetPackageModel;
import com.iskhak.padie.model.packagedata.ViewedPackage;

public interface PackageModelDAO {
	public List<PackageModel> list();
	public SetPackageModel getOrder(SetPackageModel order);
	public List<PackageModel> getNewOrders(String deviceId, Date date);
	public void setViewedPackage(ViewedPackage viewedPackage);
/*	public List<SetPackageModel> testSetPackageModel()*/
}
