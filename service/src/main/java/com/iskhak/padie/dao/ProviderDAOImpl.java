package com.iskhak.padie.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
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
		try{
			@SuppressWarnings("unchecked")
			List<Provider> providers = (List<Provider>) sessionFactory.getCurrentSession()
					.getNamedQuery("callRatedProviders")
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		    
		    System.out.println("providers size: " + providers.size());
		    return providers;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
