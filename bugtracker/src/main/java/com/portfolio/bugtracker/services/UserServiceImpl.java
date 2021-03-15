package com.portfolio.bugtracker.services;

import com.portfolio.bugtracker.models.User;
import com.portfolio.bugtracker.repositories.RoleRepository;
import com.portfolio.bugtracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userrepos;
	
	@Autowired
	private RoleRepository rolerepos;
	
	@Override public void deleteAllUsers()
	{
	
	}
	
	@Override public void deleteUserById(long id)
	{
	
	}
	
	@Override public User save(User user)
	{
		return null;
	}
	
	@Override public User update(long id, User user)
	{
		return null;
	}
	
	@Override public List<User> findAllUsers()
	{
		return null;
	}
	
	@Override public User findUserById(long id)
	{
		return null;
	}
	
	@Override public User findUserByUsername(String username)
	{
		return null;
	}
}
