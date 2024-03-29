package com.echovault.capstone.controllers;

import com.echovault.capstone.Util.Password;
import com.echovault.capstone.Util.TLSEmail;
import com.echovault.capstone.models.Echo;
import com.echovault.capstone.models.User;
import com.echovault.capstone.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import org.springframework.validation.annotation.Validated;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class HomeController {

    private final UserRepository userDao;
    private final PasswordEncoder encoder;
    private final EchoRepository echoDao;
    private final TLSEmail tlsEmail;

//    private final StorageService storageService;

//    @Autowired
//    public FileUploadController(StorageService storageService) {
//        this.storageService = storageService;
//    }


    public HomeController(UserRepository userDao, PasswordEncoder encoder, EchoRepository echoDao, TLSEmail tlsEmail) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.echoDao = echoDao;
        this.tlsEmail = tlsEmail;
    }

    @Value("${file-upload-path}")
    private String uploadPath;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(Model model){
        List<Echo> echoList = echoDao.findAll();
        model.addAttribute("echos", echoList);
        return "home";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute @Validated User user, Errors validation, @RequestParam(name = "user-profile-img")MultipartFile uploadedFile, @RequestParam(name = "confirm") String confirm, Model model) throws ServletException, IOException {
//        User user = userDao.findById(principal.getId()).get();
//        model.addAttribute("user", user);
        if(!Password.goodQualityPassword(user.getPassword())){
            validation.rejectValue(
                    "password",
                    "user.password",
                    "Password must contain at least 8 characters with one uppercase letter, one lower case letter, one number and one special character "
            );
        }
        for (User u: userDao.findAll()){
            if(u.getUsername().equalsIgnoreCase(user.getUsername())){
                validation.rejectValue(
                        "username",
                        "user.username",
                        "username already exists"
                );
            }
        }
        for (User u: userDao.findAll()){
            if(u.getEmail().equalsIgnoreCase(user.getEmail())){
                validation.rejectValue(
                        "email",
                        "user.email",
                        "email has already been registered for this site"
                );
            }
        }
        if(!user.getPassword().equals(confirm)){
            validation.rejectValue(
                    "password",
                    "user.password",
                    "passwords do not match"
            );
        }
        if(validation.hasErrors()){
            model.addAttribute("errors", validation);
            model.addAttribute("user", user);
            return "register";
        }
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
        String subject = "Thank You For Registering";
        String body = "Hello, " + user.getFirstName() +
                "! \n Thank you for registering with Echo Vault. You now have access to " +
                "creating Echos. These are pages dedicated to your loved ones. Share the link with " +
                "friends and family so they can share their memories with everyone.";
        tlsEmail.sendEmail(user.getEmail(), subject, body);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/";
    }

    @GetMapping("/forgot")
    public String forgotPassowrdForm(){
        return "forgot-password";
    }

    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam(name = "email") String email, Model model) throws ServletException, IOException {
        User user = userDao.findByEmail(email);
        if(user != null){
        String randomPassword = Password.randomGen();
//        String body = "Hello " + user.getFirstName() + ", your temporary password is "+ randomPassword +"\n\nPlease go to http://localhost:8080/reset-password";
        String body = "Hello " + user.getFirstName() + ", your temporary password is "+ randomPassword +"\n\nPlease go to http://echovault.xyz/reset-password";
        String subject = "Reset Password";
            tlsEmail.sendEmail(user.getEmail(), subject, body);
            String hash = encoder.encode(randomPassword);
            user.setPassword(hash);
            userDao.save(user);
            return "email-sent";
        }
        model.addAttribute("error", true);
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(){
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam(name="username") String username,
                                @RequestParam(name="email") String email,
                                @RequestParam(name="password") String password,
                                Model model){

        User user = userDao.findByEmail(email);
        if(user.getUsername().equals(username) && Password.check(password, user.getPassword())){
            return changePasswordForm(user, model);
        }

        return "reset-password";
    }

    @GetMapping("/change-password")
    public String changePasswordForm(User user, Model model){
        model.addAttribute("user", user);
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(name="password")String password,
                                 @RequestParam(name="confirm")String confirm,
                                 @RequestParam(name="id")long id,
                                 Model model){
        User user = userDao.getOne(id);
        if (password.equals(confirm)){
            String hash = encoder.encode(password);
            user.setPassword(hash);
            userDao.save(user);
            return "login";
        }
        return changePasswordForm(user, model);
    }




}
