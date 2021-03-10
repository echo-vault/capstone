package com.echovault.capstone.controllers;


import com.echovault.capstone.models.Echo;
import com.echovault.capstone.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EchoController {

    private final EchoRepository echoDao;
    private final MemoryRepository memoryDao;
    private final CommentRepository commentDao;
    private final ImageRepository imageDao;
    private final LinkRepository linkDao;
    private final UserRepository userDao;

    public EchoController(EchoRepository echoDao, MemoryRepository memoryDao, CommentRepository commentDao, ImageRepository imageDao, LinkRepository linkDao, UserRepository userDao) {
        this.echoDao = echoDao;
        this.memoryDao = memoryDao;
        this.commentDao = commentDao;
        this.imageDao = imageDao;
        this.linkDao = linkDao;
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }


}
