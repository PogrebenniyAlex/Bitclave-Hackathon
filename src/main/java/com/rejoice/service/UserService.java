package com.rejoice.service;

import com.rejoice.entity.token.Token;
import com.rejoice.entity.user.Role;
import com.rejoice.entity.user.User;

import java.util.Map;

public interface UserService {
    User createNewUser(User user);
    User createNewUserVendor(User user);
    User saveUser(User user);
    User findUsersByEmail(String email);
    Role findRoleByName(String name);

    Token createUser(User user);

    Map<String, Object> search(Map<String, Object> request);
}
