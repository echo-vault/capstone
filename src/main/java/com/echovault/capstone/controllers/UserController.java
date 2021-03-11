package com.echovault.capstone.controllers;

import com.echovault.capstone.models.User;
import com.echovault.capstone.repositories.UserRepository;
import com.echovault.capstone.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userDao;
    private final UserService userService;

    public UserController(UserRepository userDao, UserService userService){
        this.userDao = userDao;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model){
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String goToProfile(@ModelAttribute User user){
//        User editedUser = userService.getLoggedInUser();
//        user.setId(editedUser.getId());
        user.setPassword("password");
        userDao.save(user);
        return "redirect:/profile";
    }

}
