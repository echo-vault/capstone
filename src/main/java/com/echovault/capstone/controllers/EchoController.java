package com.echovault.capstone.controllers;


import com.echovault.capstone.models.*;
import com.echovault.capstone.repositories.*;
import com.echovault.capstone.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class EchoController {

    private final EchoRepository echoDao;
    private final MemoryRepository memoryDao;
    private final CommentRepository commentDao;
    private final ImageRepository imageDao;
    private final LinkRepository linkDao;
    private final UserRepository userDao;
    private final UserService userService;

    public EchoController(EchoRepository echoDao, MemoryRepository memoryDao, CommentRepository commentDao, ImageRepository imageDao, LinkRepository linkDao, UserRepository userDao, UserService userService) {
        this.echoDao = echoDao;
        this.memoryDao = memoryDao;
        this.commentDao = commentDao;
        this.imageDao = imageDao;
        this.linkDao = linkDao;
        this.userDao = userDao;
        this.userService = userService;
    }

    @GetMapping("/echo/{id}")
    public String viewEcho(Model model, @PathVariable long id){
        Echo echo = echoDao.getOne(id);
        User user = userService.getLoggedInUser();
        model.addAttribute("memory", new Memory());
        model.addAttribute("comment", new Comment());
        model.addAttribute("echo", echo);
        model.addAttribute("user", user);
        return "echo";
    }

    @PostMapping("/memory")
    public String createMemory(@ModelAttribute Memory memory,
                                @RequestParam(name = "echoId") long echoId,
                                @RequestParam(name = "userId") long userId
                                ){
        memory.setCreatedAt(new Date());
        memory.setUser(userDao.getOne(userId));
        memory.setEcho(echoDao.getOne(echoId));
        memoryDao.save(memory);
        return "redirect:/echo/" + memory.getEcho().getId();
    }

    @PostMapping("/comment")
    public String createComment(@ModelAttribute Comment comment,
                                @RequestParam(name = "memoryId") long memoryId,
                                @RequestParam(name = "userId") long userId
                                ){
        comment.setCreatedAt(new Date());
        comment.setUser(userDao.getOne(userId));
        comment.setMemory(memoryDao.getOne(memoryId));
        commentDao.save(comment);
        return "redirect:/echo/" + comment.getMemory().getEcho().getId();
    }






}
