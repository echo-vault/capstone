package com.echovault.capstone.repositories;

import com.echovault.capstone.models.Echo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface EchoRepository extends JpaRepository<Echo, Long> {

    @GetMapping("/Search")
    public String lectureSearch(Model model) {

        model.addAttribute("echo", new Echo());

        return "Search";
    }

    @PostMapping("/lectureSearch")
    public String Search(Echo echo, Model model, String subject) {

        List<Echo> foundLectures = lectureR.findBySubject(subject);
        model.addAttribute("foundLectures", foundLectures);

        return "lectureSearch";
    }


}
