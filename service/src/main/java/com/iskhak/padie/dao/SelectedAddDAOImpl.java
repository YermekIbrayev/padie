package com.iskhak.padie.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iskhak.padie.model.packagedata.SelectedItemsAdd;

@Repository
public class SelectedAddDAOImpl implements SelectedAddDAO {
    
	@Autowired
    private SessionFactory sessionFactory;
	
	public SelectedAddDAOImpl(){
		
	}
	
	public SelectedAddDAOImpl(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
    @Transactional
	public List<SelectedItemsAdd> list() {
		@SuppressWarnings("unchecked")
		List<SelectedItemsAdd> selectedItemsAdd = (List<SelectedItemsAdd>) sessionFactory.getCurrentSession()
				.createCriteria(SelectedItemsAdd.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        System.out.println(selectedItemsAdd.size());	
        
		return selectedItemsAdd;
	}

}
