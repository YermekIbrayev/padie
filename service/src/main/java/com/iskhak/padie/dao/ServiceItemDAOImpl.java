package com.iskhak.padie.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iskhak.padie.model.listdata.GetServiceItem;

@Repository
public class ServiceItemDAOImpl implements ServiceItemDAO {
    @Autowired
    private SessionFactory sessionFactory;
 
    public ServiceItemDAOImpl() {
         
    }
     
    public ServiceItemDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
	@Override
    @Transactional // client
	public List<GetServiceItem> list() {
		@SuppressWarnings("unchecked")
		List<GetServiceItem> serviceList = (List<GetServiceItem>) sessionFactory.getCurrentSession()
				.createCriteria(GetServiceItem.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		System.out.println(serviceList.size());
	
		return serviceList;
	}

}
