package com.iskhak.padie.dao;

import java.util.List;

import org.springframework.mobile.device.Device;

import com.iskhak.padie.model.security.User;
import com.iskhak.padie.security.JwtAuthenticationRequest;

public interface UserDAO {
	public List<User> list();
	
	public User get(int id);
	
	public void saveOrUpdate(User user);
	
	public void delete(long id);
	
	public String login(JwtAuthenticationRequest authenticationRequest);
	
	public String register(User user);
}
