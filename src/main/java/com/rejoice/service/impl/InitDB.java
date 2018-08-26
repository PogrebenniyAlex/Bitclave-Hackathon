package com.rejoice.service.impl;

import com.rejoice.dao.user.RoleDao;
import com.rejoice.entity.enums.Roles;
import com.rejoice.entity.user.Role;
import com.rejoice.entity.user.User;
import com.rejoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitDB implements ApplicationRunner{

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        List<Role> roles = roleDao.findAll();

        if(roles.isEmpty()){
            Role roleUser = Role.builder()
                    .authority(Roles.USER.name())
                    .build();

            Role roleAdmin = Role.builder()
                    .authority(Roles.ADMIN.name())
                    .build();

            Role roleVendor = Role.builder()
                    .authority(Roles.VENDOR.name())
                    .build();

            List<Role> newRoleList = new ArrayList<>();

            newRoleList.add(roleUser);
            newRoleList.add(roleAdmin);
            newRoleList.add(roleVendor);

            roleDao.saveAll(newRoleList);
        }

        String email = "vendor@gmail.com";
        String password = "qwerty";

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        User usersByEmail = userService.findUsersByEmail(email);

        if(usersByEmail== null){
            userService.createNewUserVendor(user);
        }

    }
}

