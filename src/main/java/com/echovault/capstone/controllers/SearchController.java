package com.echovault.capstone.controllers;


import com.echovault.capstone.models.Echo;
import com.echovault.capstone.repositories.EchoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SearchController {

    private final EchoRepository echoDao;

    public SearchController(EchoRepository echoDao) {
        this.echoDao = echoDao;
    }

    @GetMapping("/search")
    public String search(){
        return "search";
    }

    @PostMapping("/Search/{id}")
    public String Search(Echo echo, Model model, @PathVariable long id) {

        List<Echo> foundEchos = echoDao.findAll();
        model.addAttribute("foundEchos", echo.getLastName());

        return "Search";
    }

}
