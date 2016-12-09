package com.iskhak.padie.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iskhak.padie.model.packagedata.PackageModel;
import com.iskhak.padie.model.packagedata.SetPackageModel;
import com.iskhak.padie.model.packagedata.SetSelectedItems;
import com.iskhak.padie.model.packagedata.SetSelectedItemsAdd;
import com.iskhak.padie.model.packagedata.SetSelectedItemsAddExtra;
import com.iskhak.padie.model.packagedata.ViewedPackage;
 
@Repository
public class PackageModelDAOImpl implements PackageModelDAO{
    @Autowired
    private SessionFactory sessionFactory;
 
    public PackageModelDAOImpl() {
         
    }
     
    public PackageModelDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
	@Override
    @Transactional
	public List<PackageModel> list() {
		@SuppressWarnings("unchecked")
		List<PackageModel> packageList = (List<PackageModel>) sessionFactory.getCurrentSession()
				.createCriteria(PackageModel.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
        System.out.println(packageList.size());	
        
		return packageList;
	}

	@Override
	@Transactional
	public SetPackageModel getOrder(SetPackageModel order) {
		order.setPrice(100.0f);
		for(SetSelectedItems item:order.getSelectedItems()){
			item.setPackageModel(order);
		}
		
		for(SetSelectedItemsAdd item:order.getSelectedItemsAdd()){
			item.setPackageModel(order);
		}
		
		for(SetSelectedItemsAddExtra item:order.getSelectedItemsAddExtra()){
			item.setPackageModel(order);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(order);
		System.out.println(order.getSelectedItems().get(0).getPackageModel().getId());
		return order;
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PackageModel> getNewOrders(String deviceId, Date date) {
		System.out.println(date);
		String hql = "Select p from PackageModel as p "
				+ "left join  p.viewedList as v on (v.deviceId=:deviceId and  v.viewed<:date)  "
				+ "where p.acceptedDate is null and v.id is null ";

		List<PackageModel> result = (List<PackageModel>) sessionFactory.getCurrentSession()
/*				.createCriteria(PackageModel.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.isNull("acceptedDate")) //where acceptedDate is Null*/			
		.createQuery(hql)
		.setParameter("deviceId", deviceId)
		.setTimestamp("date", date)
		.list();
		
		hql = "Select p, max(v.viewed) form PackageModel as p "
				+ "left join p.viewedList as v on (v.deviceId=:deviceId) "
				+ "where p.acceptedDate is null"
				+ "group by v.selectedPkg";
		
/*		List<Object[]> objects = (List<Object[]>) sessionFactory.getCurrentSession()
				.createQuery("hql")
				.setParameter("deviceId", deviceId)
				.list();*/
		
		//System.out.println(objects.size());
		for(PackageModel item:result){
			List<ViewedPackage> totalList = item.getViewedList();
			List<ViewedPackage> viewedList=new ArrayList<>();
			for(ViewedPackage viewedItem:totalList){
				if(viewedItem.getDeviceId().equals(deviceId))
					viewedList.add(viewedItem);
			}
			if(viewedList!=null&&!viewedList.isEmpty())
				item.setViewed(Collections.max(viewedList).getViewed());
		}
		
		return result;
	}
	
	@Override
	@Transactional
	public void setViewedPackage(ViewedPackage viewedPackage){
		@SuppressWarnings("unchecked")
		List<ViewedPackage> checkList = (List<ViewedPackage>) sessionFactory.getCurrentSession()
				.createCriteria(ViewedPackage.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.eq("deviceId", viewedPackage.getDeviceId()))
				.add(Restrictions.eq("selectedPkg", viewedPackage.getSelectedPkg()))
				.list();
		if(checkList==null||checkList.isEmpty())
			sessionFactory.getCurrentSession().save(viewedPackage);
		System.out.println("Viewed package:"+viewedPackage.getId()+" viewed"+viewedPackage.getViewed());
	}

/*	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SetPackageModel> testSetPackageModel() {
		List<SetPackageModel> result = (List<SetPackageModel>) sessionFactory.getCurrentSession()
				.createCriteria(SetPackageModel.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		return result;
	}*/
}
