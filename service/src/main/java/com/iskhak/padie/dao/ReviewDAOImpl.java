package com.iskhak.padie.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iskhak.padie.config.Constants;
import com.iskhak.padie.model.Review;

@Repository
public class ReviewDAOImpl implements ReviewDAO{
    @Autowired
    private SessionFactory sessionFactory;
    
	@Override
	@Transactional
	public boolean addReview(Review review) {
		review.setCreated(new Date());
		try{
			sessionFactory.getCurrentSession().update(review);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public List<Review> getReviews(int pid, boolean isFullList) {
		
		Criteria criteria = sessionFactory.getCurrentSession()
				.createCriteria(Review.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.add(Restrictions.and(
						Restrictions.eq("pid", pid),
						Restrictions.isNotNull("created")
						));
		if(!isFullList){
			criteria.setMaxResults(Constants.REVIEWS_SHORT_NUM);
		}
		
		@SuppressWarnings("unchecked")
		List<Review> reviewList = criteria.list();
		return reviewList;
	}



}
