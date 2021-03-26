package com.echovault.capstone.services;

import com.echovault.capstone.models.User;
import com.echovault.capstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    private final UserRepository userDao;

    public UserService(UserRepository userDao) {
        this.userDao = userDao;
    }

    public User getLoggedInUser(){
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return user;
        } catch (ClassCastException e){
            e.printStackTrace();
        }
         User user = new User();
        user.setId(0);
        return user;
    }
}
