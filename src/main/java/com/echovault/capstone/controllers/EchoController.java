package com.echovault.capstone.controllers;


import com.echovault.capstone.models.Echo;
import com.echovault.capstone.models.Image;
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

    @GetMapping("/echo/{id}")
    public String viewEcho(Model model, @PathVariable long id){
        Echo echo = echoDao.getOne(id);
        model.addAttribute("echo", echo);
        model.addAttribute("carousel", echo.getImages());
        for(Image i: echo.getImages()){
            System.out.println(i.getPath());
        }
        return "echo";
    }




}
