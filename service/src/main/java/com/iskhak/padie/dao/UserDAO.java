package com.iskhak.padie.dao;

import java.util.List;

import com.iskhak.padie.model.security.User;

public interface UserDAO {
	public List<User> list();
	
	public User get(int id);
	
	public void saveOrUpdate(User user);
	
	public void delete(long id);
	
	public boolean login(User user);
}
