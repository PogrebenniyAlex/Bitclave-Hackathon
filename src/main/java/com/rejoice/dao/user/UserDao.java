package com.rejoice.dao.user;


import com.rejoice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    User findUserByEmail(String email);
    User findUsersByEmail(String email);
}
