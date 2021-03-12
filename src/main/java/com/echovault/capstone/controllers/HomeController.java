package com.echovault.capstone.controllers;

import com.echovault.capstone.models.Echo;
import com.echovault.capstone.models.User;
import com.echovault.capstone.repositories.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;

    public HomeController(UserRepository userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user){
        String password = user.getPassword();
        String hash = encoder.encode(password);
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/home";
    }

}
