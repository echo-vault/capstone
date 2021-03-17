package com.echovault.capstone.controllers;



import com.echovault.capstone.models.*;
import com.echovault.capstone.repositories.*;
import com.echovault.capstone.services.UserService;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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
  
    @Value("${file-upload-path}")
    private String uploadPath;

    @GetMapping("/echo-create")
    public String showCreateForm(Model model) {
        model.addAttribute("echo", new Echo());
        return "echo-create";
    }

    @PostMapping("/echo-create")
    public String saveFile(@ModelAttribute Echo echo,
                           @RequestParam(name = "profileImg") MultipartFile profileImg,
                           @RequestParam(name = "bgImg") MultipartFile bgImg,
                           @RequestParam(name = "image") ArrayList<MultipartFile> images,
                           @RequestParam(name = "linkName1", defaultValue = "") String linkName1,
                           @RequestParam(name = "link1", defaultValue = "") String link1,
                           @RequestParam(name = "linkName2", defaultValue = "") String linkName2,
                           @RequestParam(name = "link2", defaultValue = "") String link2,
                           @RequestParam(name = "linkName3", defaultValue = "") String linkName3,
                           @RequestParam(name = "link3", defaultValue = "") String link3,
                           Model model)
        {
         if (profileImg != null) {
            String filename = profileImg.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                profileImg.transferTo(destinationFile);
                echo.setProfileImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        if (bgImg != null) {
            String filename = bgImg.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                bgImg.transferTo(destinationFile);
                echo.setBackgroundImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            echo.setBackgroundImage("/img/sunset1.jpg");
        }
        echo.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        echo.setCreatedAt(new Date());
        echoDao.save(echo);
        if (images != null) {
            for(MultipartFile image: images){
                System.out.println(image);
                String filename = image.getOriginalFilename();
                String filepath = Paths.get(uploadPath, filename).toString();
                File destinationFile = new File(filepath);
                try {
                    image.transferTo(destinationFile);
                    Image img = new Image();
                    img.setPath("/uploads/" + filename);
                    img.setEcho(echo);
                    imageDao.save(img);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
        if(link1.length() > 0 && linkName1.length() > 0) {
            Link link = new Link();
            link.setName(linkName1);
            link.setUrl(link1);
            link.setEcho(echo);
            linkDao.save(link);
        } else if(link2.length() > 0 && linkName2.length() > 0) {
            Link link = new Link();
            link.setName(linkName2);
            link.setUrl(link2);
            link.setEcho(echo);
            linkDao.save(link);
        } else if(link3.length() > 0 && linkName3.length() > 0) {
            Link link = new Link();
            link.setName(linkName3);
            link.setUrl(link3);
            link.setEcho(echo);
            linkDao.save(link);
        }
        return "redirect:/echo/" + echo.getId();
    }

    @GetMapping("/echo/{id}/edit")
    public String showEditForm(Model model, @PathVariable long id) {
        Echo echo = echoDao.getOne(id);
        User sessionUser = userService.getLoggedInUser();
        User user = userDao.getOne(sessionUser.getId());
        model.addAttribute("echo", echo);
        return "echo-edit";
    }

    @PostMapping("/echo/{id}/edit")
    public String editEcho(@PathVariable long id,
                           @RequestParam(name = "profileImg") MultipartFile profileImg,
                           @RequestParam(name = "bgImg") MultipartFile bgImg,
                           @RequestParam(name = "image") ArrayList<MultipartFile> images,
                           @RequestParam(name = "current-carousel") List<Image> currentImages,
                           @RequestParam(name = "current-profile") String profileImgPath,
                           @RequestParam(name = "current-background") String bgImgPath,
                           @RequestParam(name = "current-links") List<Link> currentLinks,
                           @RequestParam(name = "linkName1", defaultValue = "") String linkName1,
                           @RequestParam(name = "link1", defaultValue = "") String link1,
                           @RequestParam(name = "linkName2", defaultValue = "") String linkName2,
                           @RequestParam(name = "link2", defaultValue = "") String link2,
                           @RequestParam(name = "linkName3", defaultValue = "") String linkName3,
                           @RequestParam(name = "link3", defaultValue = "") String link3,
                           Model model){

        Echo echo = echoDao.getOne(id);
        model.addAttribute("echo", echo);
        echo.setBackgroundImage(bgImgPath);
        echo.setProfileImage(profileImgPath);
        echo.setImages(currentImages);
        echo.setLinks(currentLinks);

        if (profileImg != null) {
            String filename = profileImg.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                profileImg.transferTo(destinationFile);
                echo.setProfileImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        if (bgImg != null) {
            String filename = bgImg.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                bgImg.transferTo(destinationFile);
                echo.setBackgroundImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            echo.setBackgroundImage("/img/sunset1.jpg");
        }
        if (images != null) {
            for(MultipartFile image: images){
                System.out.println(image);
                String filename = image.getOriginalFilename();
                String filepath = Paths.get(uploadPath, filename).toString();
                File destinationFile = new File(filepath);
                try {
                    image.transferTo(destinationFile);
                    Image img = new Image();
                    img.setPath("/uploads/" + filename);
                    img.setEcho(echo);
                    imageDao.save(img);
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
        if(link1.length() > 0 && linkName1.length() > 0) {
            Link link = new Link();
            link.setName(linkName1);
            link.setUrl(link1);
            link.setEcho(echo);
            linkDao.save(link);
        } else if(link2.length() > 0 && linkName2.length() > 0) {
            Link link = new Link();
            link.setName(linkName2);
            link.setUrl(link2);
            link.setEcho(echo);
            linkDao.save(link);
        } else if(link3.length() > 0 && linkName3.length() > 0) {
            Link link = new Link();
            link.setName(linkName3);
            link.setUrl(link3);
            link.setEcho(echo);
            linkDao.save(link);
        }
        return "redirect:/echo/" + echo.getId();
    }

    @GetMapping("/echo/{id}")
    public String viewEcho(Model model, @PathVariable long id){
        Echo echo = echoDao.getOne(id);
        User user = userService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("memory", new Memory());
        model.addAttribute("comment", new Comment());
        model.addAttribute("echo", echo);
        return "echo";
    }

    @PostMapping("/echo/{id}/delete")
    public String deleteEcho(@PathVariable long id, RedirectAttributes redirectAttributes){
        echoDao.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Echo successfully deleted.");
        return "redirect:/profile";
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






