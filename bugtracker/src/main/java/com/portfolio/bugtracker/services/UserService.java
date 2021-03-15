package com.portfolio.bugtracker.services;

import com.portfolio.bugtracker.models.User;

import java.util.List;

public interface UserService
{
	void deleteAllUsers();
	
	void deleteUserById(long id);
	
	User save(User user);
	
	User update(long id, User user);
	
	List<User> findAllUsers();
	
	User findUserById(long id);
	
	User findUserByUsername(String username);
}
