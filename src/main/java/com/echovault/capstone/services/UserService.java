package com.echovault.capstone.services;

import com.echovault.capstone.models.User;
import com.echovault.capstone.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    private final UserRepository usersDao;

    public UserService(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    public User loggedInUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
