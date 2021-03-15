package com.portfolio.bugtracker.services;

import com.portfolio.bugtracker.models.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService
{
	@Override public void deleteAllRoles()
	{
	
	}
	
	@Override public void deleteRoleById(long id)
	{
	
	}
	
	@Override public void deleteRoleByType(String type)
	{
	
	}
	
	@Override public Role save(Role role)
	{
		return null;
	}
	
	@Override public Role update(long id, Role role)
	{
		return null;
	}
	
	@Override public List<Role> findAllRoles()
	{
		return null;
	}
	
	@Override public Role findRoleById(long id)
	{
		return null;
	}
	
	@Override public Role findRoleByType(String type)
	{
		return null;
	}
}
