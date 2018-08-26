package com.rejoice.dao.user;


import com.rejoice.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoleDao extends JpaRepository<Role, Long> {
    Role findRoleByAuthority(String authority);
}
