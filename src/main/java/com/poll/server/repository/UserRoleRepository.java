package com.poll.server.repository;

 

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 
import com.poll.server.model.UserRoles;
 
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {
	
	 
	 
}
