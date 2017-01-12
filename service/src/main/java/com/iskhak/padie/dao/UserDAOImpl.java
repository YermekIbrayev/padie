package com.iskhak.padie.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.iskhak.padie.config.Constants;
import com.iskhak.padie.model.security.Authority;
import com.iskhak.padie.model.security.AuthorityName;
import com.iskhak.padie.model.security.User;
import com.iskhak.padie.security.JwtAuthenticationRequest;
import com.iskhak.padie.security.JwtTokenUtil;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
 
    public UserDAOImpl() {
         
    }
     
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
 
    @Override
    @Transactional
    public List<User> list() {
        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) sessionFactory.getCurrentSession()
                .createCriteria(User.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
 
        return listUser;
    }
 
    @Override
    @Transactional
    public void saveOrUpdate(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }
 
    @Override
    @Transactional
    public void delete(long id) {
        User userToDelete = new User();
        userToDelete.setId(id);
        sessionFactory.getCurrentSession().delete(userToDelete);
    }
 
    @Override
    @Transactional
    public User get(int id) {
        String hql = "from User where id=" + id;
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
         
        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) query.list();
         
        if (listUser != null && !listUser.isEmpty()) {
            return listUser.get(0);
        }
         
        return null;
    }
    
    @Override
    @Transactional
    public String login(JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
/*    	boolean result = false;
    	if(user.getUsername().equals("root")&&user.getPassword().equals("root"))
    		result = true;*/
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
       
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        String hql = "from User where email='" + userDetails.getUsername() +"'";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) query.list(); 
        if (listUser == null || listUser.isEmpty()) {
        	return Constants.NO_SUCH_USER;
        }
        
        final String result = jwtTokenUtil.generateToken(userDetails, listUser.get(0).getId());
    	return result;
    }
    
    
    @Transactional
    @Override
    public String register(User user){
    	String password = user.getPassword();
    	user.setPassword((new BCryptPasswordEncoder()).encode(password));
    	
    	String hql = "from User where email='" + user.getEmail() +"'";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        
        @SuppressWarnings("unchecked")
        List<User> listUser = (List<User>) query.list(); 
        if (listUser != null && !listUser.isEmpty()) {
        	return Constants.EMAIL_ALREADY_IN_USE;
        }
        
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        List<User> users = new ArrayList<>();
        users.add(user);
        
        Authority authority = new Authority(AuthorityName.ROLE_USER, users);
        
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        
        user.setAuthorities(authorities);
        sessionFactory.getCurrentSession().saveOrUpdate(user);
         
        return Constants.RESPONSE_OK;
    	
    }

}
