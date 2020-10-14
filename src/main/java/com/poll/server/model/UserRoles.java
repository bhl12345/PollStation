package com.poll.server.model;

 
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoles {
	
    @Id
    private Long user_id;
    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }
    
    
    private Long role_id;
    public Long getRoleId() {
        return role_id;
    }

    public void setRoleId(Long role_id) {
        this.role_id = role_id;
    }

}
