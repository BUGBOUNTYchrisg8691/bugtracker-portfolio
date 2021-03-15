package com.portfolio.bugtracker.services;

import com.portfolio.bugtracker.models.Role;

import java.util.List;

public interface RoleService
{
	void deleteAllRoles();
	
	void deleteRoleById(long id);
	
	void deleteRoleByType(String type);
	
	Role save(Role role);
	
	Role update(long id, Role role);
	
	List<Role> findAllRoles();
	
	Role findRoleById(long id);
	
	Role findRoleByType(String type);
}
