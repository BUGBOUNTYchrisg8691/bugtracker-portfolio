package com.portfolio.bugtracker.repositories;

import com.portfolio.bugtracker.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>
{
	Role findByType(String type);
}
