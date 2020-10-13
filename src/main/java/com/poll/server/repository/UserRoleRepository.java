package com.poll.server.repository;

 

import org.springframework.data.jpa.repository.JpaRepository;

import com.poll.server.model.UserRoles;
 

public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {
	boolean findUserId(Long long1);
}
