package com.echovault.capstone.controllers;


import com.echovault.capstone.models.Echo;
import com.echovault.capstone.repositories.EchoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    private final EchoRepository echoDao;

    public SearchController(EchoRepository echoDao) {
        this.echoDao = echoDao;
    }

    @GetMapping("/search")
    public String search(Model model){
        List<Echo> echoList = echoDao.findAll();

//        model.addAttribute("firstName", "All Echos");
        model.addAttribute("echos", echoList);
        return "search";
    }

    @PostMapping("/search")
    public String Search(Echo echo, Model model, @RequestParam (name = "search")String search) {

        List<Echo> foundEchos = echoDao.findAll();
        List<Echo> matchedEchos = new ArrayList<>();
        for(Echo e : foundEchos){
            if(e.getLastName().equalsIgnoreCase(search)){
                matchedEchos.add(e);
            }
            if(e.getFirstName().equalsIgnoreCase(search)){
                matchedEchos.add(e);
            }
            if(e.getFullName().equalsIgnoreCase(search)){
                matchedEchos.add(e);
            }
        }
        model.addAttribute("echos", matchedEchos);

        return "search";
    }

}
