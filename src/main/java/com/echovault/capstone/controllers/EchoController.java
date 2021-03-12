package com.echovault.capstone.controllers;


import com.echovault.capstone.Util.FileUpload;
import com.echovault.capstone.models.Echo;
import com.echovault.capstone.models.User;
import com.echovault.capstone.models.Image;
import com.echovault.capstone.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
public class EchoController {

//    private final EchoRepository echoDao;
//    private final MemoryRepository memoryDao;
//    private final CommentRepository commentDao;
//    private final ImageRepository imageDao;
//    private final LinkRepository linkDao;
//    private final UserRepository userDao;
//
//    public EchoController(EchoRepository echoDao, MemoryRepository memoryDao, CommentRepository commentDao, ImageRepository imageDao, LinkRepository linkDao, UserRepository userDao) {
//        this.echoDao = echoDao;
//        this.memoryDao = memoryDao;
//        this.commentDao = commentDao;
//        this.imageDao = imageDao;
//        this.linkDao = linkDao;
//        this.userDao = userDao;
//    }
//    @Value("${file-upload-path}")
//    private String uploadPath;
//
//    @GetMapping("/echo-create")
//    public String showCreateForm() {
//        return "echo-create";
//    }
//
//
//    @PostMapping("/echo-create")
//    public String saveFile(@ModelAttribute Echo echo,
//                           @RequestParam(name = "profile-img") MultipartFile uploadedFile,
//                           @RequestParam(name = "background-img") MultipartFile uploadedFile2,
//                           @RequestParam(name = "carousel-img") MultipartFile uploadedFile3,
//                           Model model
//
//    @GetMapping("/echo/{id}")
//    public String viewEcho(Model model, @PathVariable long id){
//        Echo echo = echoDao.getOne(id);
//        model.addAttribute("echo", echo);
//        model.addAttribute("carousel", echo.getImages());
//        model.addAttribute("links", echo.getLinks());
//        for(Image i: echo.getImages()){
//            System.out.println(i.getPath());
//        }
//        return "echo";
//    }
//
//
//
//
//    ) {
//        FileUpload.savedFile(uploadedFile, echo, uploadPath);
//        FileUpload.savedFile(uploadedFile2, echo, uploadPath);
//        FileUpload.savedFile(uploadedFile3, echo, uploadPath);
//
//        echo.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        Echo savedEcho = echoDao.save(echo);
//        String subject = "New Post Created!";
////        String body = "Dear " + savedPost.getUser().getUsername() + ". Thank you for creating a post. Your post id is: " + savedPost.getId();
////
////        emailService.prepareAndSend(savedEcho, subject, body);
//        model.addAttribute("message", "File successfully uploaded!");
//        return "redirect:/echo-create";
//
//    }
}






