package com.iskhak.padie.dao;

import java.util.List;

import com.iskhak.padie.model.User;

public interface UserDAO {
	public List<User> list();
	
	public User get(int id);
	
	public void saveOrUpdate(User user);
	
	public void delete(int id);
	
	public boolean login(User user);
}
