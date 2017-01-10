package com.iskhak.padie.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iskhak.padie.model.security.Provider;

@Repository
public class ProviderDAOImpl implements ProviderDAO{
    @Autowired
    private SessionFactory sessionFactory;

    //should be refactored
	@Override
	@Transactional 
	public List<Provider> list() {
		String hql = "select u.id, u.username, avg(p.rating)  from User u" +
						"left join SetPackageModel p on u.id = p.providerID group by u.id";
		
	Iterator<?> providerQuery = sessionFactory.getCurrentSession()
			.createQuery(hql)
	            .list()
	            .iterator();

	List<Provider> providers = new ArrayList<>();
	
	while ( providerQuery.hasNext() ) {
	    Object[] row = (Object[]) providerQuery.next();
	    Provider provider = new Provider(
	    		(Long)row[0], 
	    		(String) row[1], 
	    		(Float) row[2]);
	    
	    System.out.println("provider: " + provider.getName() + " rating:"+provider.getRating());
	    providers.add(provider);
	}
		return null;
	}

}
