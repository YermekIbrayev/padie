package com.iskhak.padie.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
    @Transactional // not using
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
	@Transactional // client
	public SetPackageModel setOrder(SetPackageModel order, long id) {
		System.out.println(order.getProviderID());
		order.setOrderDate(new Date());
		order.setClientId(id);
		sessionFactory.getCurrentSession().update(order);
		
		return order;
	}
	
	@Override
	@Transactional // client
	public SetPackageModel getOrderPrice(SetPackageModel order){
		order.setPrice(100.0f);
		order.setOrderDate(null);
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
	@Transactional // provider
	public List<PackageModel> getNewOrders(String deviceId, Date date, long id) {
		System.out.println(date);
		
		//get new orders
		String hql = "Select p from PackageModel as p "
				+ "left join  p.viewedList as v on ((v.deviceId=:deviceId or v.providerId=:providerId) and  v.viewed<=:date)  "
				+ "where "+/*p.acceptedDate is null and*/" v.id is null and p.orderDate is not null and (p.providerID=:providerId or ((p.providerID is null or p.providerID<1) and p.acceptedDate is null))";

		List<PackageModel> result = (List<PackageModel>) sessionFactory.getCurrentSession()
/*				.createCriteria(PackageModel.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.isNull("acceptedDate")) //where acceptedDate is Null*/			
		.createQuery(hql)
		.setParameter("deviceId", deviceId)
		.setParameter("providerId", id)
		.setTimestamp("date", date)
		.list();
		
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
	@Transactional // provider
	public void setViewedPackage(ViewedPackage viewedPackage, long id){
		viewedPackage.setViewed(new Date());
		viewedPackage.setProviderId(id);
		@SuppressWarnings("unchecked")
		List<ViewedPackage> checkList = (List<ViewedPackage>) sessionFactory.getCurrentSession()
				.createCriteria(ViewedPackage.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.and(
						Restrictions.eq("deviceId", viewedPackage.getDeviceId()),
						Restrictions.eq("selectedPkg", viewedPackage.getSelectedPkg()),
						Restrictions.eq("providerId", viewedPackage.getProviderId())))
				.list();
		if(checkList==null||checkList.isEmpty())
			sessionFactory.getCurrentSession().save(viewedPackage);
		System.out.println("Viewed package:"+viewedPackage.getId()+" viewed"+viewedPackage.getViewed());
	}

/*	@SuppressWarnings("unchecked")*/
	@Override
	@Transactional // provider
	public boolean acceptOrder(int pkgId, long id) {
		
		if(pkgId<1)
			return false;
		
		if(sessionFactory.getCurrentSession().get(SetPackageModel.class, pkgId)==null){
			return false;
		}
		
		String hql = "update SetPackageModel u set acceptedDate=:date, providerID = :providerID  where u.pkgID=:pkgId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setTimestamp("date", new Date());
		query.setLong("providerID", id);
		query.setInteger("pkgId", pkgId);
		query.executeUpdate();
		return true;
	}
	
	@Override
	@Transactional // provider
	public boolean finishOrder(int pkgId, long id) {
		
		if(pkgId<1)
			return false;
		
		if(sessionFactory.getCurrentSession().get(SetPackageModel.class, pkgId)==null){
			return false;
		}
		
		String hql = "update SetPackageModel u set finishedDate=:date where u.pkgID=:pkgId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setTimestamp("date", new Date());
		query.setInteger("pkgId", pkgId);
		query.executeUpdate();
		return true;
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
