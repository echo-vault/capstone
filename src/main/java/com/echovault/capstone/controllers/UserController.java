package com.echovault.capstone.controllers;

import com.echovault.capstone.models.User;
import com.echovault.capstone.repositories.UserRepository;
import com.echovault.capstone.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class UserController {

    private final UserRepository userDao;
    private final UserService userService;

    public UserController(UserRepository userDao, UserService userService){
        this.userDao = userDao;
        this.userService = userService;
    }

    @Value("${file-upload-path}")
    private String uploadPath;

    @GetMapping("/profile")
    public String showProfile(Model model){
        User sessionUser = userService.getLoggedInUser();
        User user = userDao.getOne(sessionUser.getId());
        model.addAttribute("echoes", user.getEchoes());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model){
        User sessionUser = userService.getLoggedInUser();
        User user = userDao.getOne(sessionUser.getId());
        model.addAttribute("user", user);
        return "profile-edit";
    }

    @PostMapping("/profile/edit")
    public String goToProfile(@ModelAttribute User user,
                              @RequestParam(name = "user-profile-img-edit") MultipartFile uploadedFile,
                              @RequestParam(name = "current-image") String imagePath,
                              Model model){

        User sessionUser = userService.getLoggedInUser();
        User editedUser = userDao.getOne(sessionUser.getId());
        user.setImage(imagePath);

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

        user.setId(editedUser.getId());
        user.setPassword(editedUser.getPassword());
        userDao.save(user);
        return "redirect:/profile";
    }

}
