package com.echovault.capstone.controllers;

import com.echovault.capstone.StorageService;
import com.echovault.capstone.Util.Password;
import com.echovault.capstone.Util.TLSEmail;
import com.echovault.capstone.models.User;
import com.echovault.capstone.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class HomeController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;
//    private final StorageService storageService;

//    @Autowired
//    public FileUploadController(StorageService storageService) {
//        this.storageService = storageService;
//    }


    public HomeController(UserRepository userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Value("${file-upload-path}")
    private String uploadPath;

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
    public String createUser(@ModelAttribute User user, @RequestParam(name = "user-profile-img") MultipartFile uploadedFile, Model model){
//        User user = userDao.findById(principal.getId()).get();
//        model.addAttribute("user", user);
        if(uploadedFile != null) {
            String fileName = uploadedFile.getOriginalFilename();
            String filePath = Paths.get(uploadPath, fileName).toString();
            File destinationFile = new File(filePath);
            try {
                uploadedFile.transferTo(destinationFile);
                user.setImage("/uploads/" + fileName);
                model.addAttribute("message", "File successfully uploaded!");
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Oops! Something went wrong! " + e);
            }
        }
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

    @GetMapping("/forgot")
    public String forgotPassowrdForm(){
        return "forgot-password";
    }

    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam(name = "email") String email) throws ServletException, IOException {
        User user = userDao.findByEmail(email);
        if(user != null){
            TLSEmail.sendEmail(user.getEmail(), user.getFirstName());
            String passwordGen = Password.getThePassword().get(0);
            user.setPassword(passwordGen);
            userDao.save(user);
        }
        return "email-sent";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(){
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam(name="username") String username,
                                @RequestParam(name="email") String email,
                                @RequestParam(name="password") String password){
        return "reset-password";
    }


}
