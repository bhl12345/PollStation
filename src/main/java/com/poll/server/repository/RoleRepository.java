package com.poll.server.repository;

import com.poll.server.model.Role;
import com.poll.server.model.RoleName;
import com.poll.server.model.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 *  
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
    Optional<Role> findByName(Role id);
    
    
}
