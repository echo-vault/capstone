package com.echovault.capstone.services;

import com.echovault.capstone.models.User;
import com.echovault.capstone.models.UserWithRoles;
import com.echovault.capstone.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsLoader implements UserDetailsService {

    private final UserRepository usersDao;

    public UserDetailsLoader(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("No user found for username: " + username);
        }
        UserDetails enhancedUser = new UserWithRoles(user);
        return enhancedUser;

    }
}
