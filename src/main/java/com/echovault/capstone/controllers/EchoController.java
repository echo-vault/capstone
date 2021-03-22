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
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String saveFile(@ModelAttribute @Validated Echo echo,
                           Errors validation,
                           @RequestParam(name = "profileImg") MultipartFile profileImg,
                           @RequestParam(name = "bgImg") MultipartFile bgImg,
                           @RequestParam(name = "image") ArrayList<MultipartFile> images,
                           @RequestParam(name = "linkName1", defaultValue = "") String linkName1,
                           @RequestParam(name = "link1", defaultValue = "") String link1,
                           @RequestParam(name = "linkName2", defaultValue = "") String linkName2,
                           @RequestParam(name = "link2", defaultValue = "") String link2,
                           @RequestParam(name = "linkName3", defaultValue = "") String linkName3,
                           @RequestParam(name = "link3", defaultValue = "") String link3,
//                           @RequestParam(name = "restingPlace", defaultValue = "") String restingPlace,
                           Model model)
        {
            if(validation.hasErrors()){
                model.addAttribute("errors", validation);
                model.addAttribute("echo", echo);
                return "echo-create";
            }
          echo.setProfileImage("/img/genericProfileImage.png");
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
         echo.setBackgroundImage("/img/sunset1.jpg");
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

//        if(restingPlace != null){
//            echo.setRestingPlace(restingPlace);
//            echoDao.save(echo);
//        }

        if(link1.length() > 0 && linkName1.length() > 0) {
            Link linkA = new Link();
            linkA.setName(linkName1);
            linkA.setUrl(link1);
            linkA.setEcho(echo);
            linkDao.save(linkA);
        }
        if(link2.length() > 0 && linkName2.length() > 0) {
            Link linkB = new Link();
            linkB.setName(linkName2);
            linkB.setUrl(link2);
            linkB.setEcho(echo);
            linkDao.save(linkB);
        }
        if(link3.length() > 0 && linkName3.length() > 0) {
            Link linkC = new Link();
            linkC.setName(linkName3);
            linkC.setUrl(link3);
            linkC.setEcho(echo);
            linkDao.save(linkC);
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
                           @ModelAttribute Echo echoToUpdate,
                           @RequestParam(name = "profileImg", required = false) MultipartFile profileImg,
                           @RequestParam(name = "bgImg", required = false) MultipartFile bgImg,
                           @RequestParam(name = "image", required = false) ArrayList<MultipartFile> images,
                           @RequestParam(name = "linkName0", defaultValue = "") String linkName0,
                           @RequestParam(name = "link0", defaultValue = "") String link0,
                           @RequestParam(name = "linkName1", defaultValue = "") String linkName1,
                           @RequestParam(name = "link1", defaultValue = "") String link1,
                           @RequestParam(name = "linkName2", defaultValue = "") String linkName2,
                           @RequestParam(name = "link2", defaultValue = "") String link2) {

        Echo echo = echoDao.getOne(id);
        echo.setImages(echoToUpdate.getImages());
        echo.setRestingPlace(echoToUpdate.getRestingPlace());
        echo.setBirthDate(echoToUpdate.getBirthDate());
        echo.setDeathDate(echoToUpdate.getDeathDate());
        echo.setMemories(echoToUpdate.getMemories());
        echo.setFirstName(echoToUpdate.getFirstName());
        echo.setLastName(echoToUpdate.getLastName());
        if(!echoToUpdate.getSummary().isEmpty()) {
            echo.setSummary(echoToUpdate.getSummary());
        }
        if (!profileImg.isEmpty()) {
            String filename = profileImg.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                profileImg.transferTo(destinationFile);
                echo.setProfileImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if(echo.getProfileImage().isEmpty())
                echo.setProfileImage("/img/sunset1.jpg");
        }
        if (!bgImg.isEmpty()) {
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
            if(echo.getBackgroundImage().isEmpty())
            echo.setBackgroundImage("/img/sunset1.jpg");
        }
        if (!images.isEmpty()) {
            for(MultipartFile image: images){
                System.out.println(image);
                if(!image.isEmpty()) {
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
        }
        //UPDATING LINKS
//        List<Link> links = echo.getLinks();
//
//        if(links != null) {
//            if (links.get(0) != null) {
//                if (link0.length() > 0 && linkName0.length() > 0) {
//                    Link linkA = linkDao.getOne(links.get(0).getId());
//                    linkA.setName(linkName0);
//                    linkA.setUrl(link0);
//                    linkA.setEcho(echo);
//                    linkDao.save(linkA);
//                }
//            }
//
//            if (links.get(1) != null) {
//                if (link1.length() > 0 && linkName1.length() > 0) {
//                    Link linkB = linkDao.getOne(links.get(1).getId());
//                    linkB.setName(linkName2);
//                    linkB.setUrl(link2);
//                    linkB.setEcho(echo);
//                    linkDao.save(linkB);
//                }
//            }
//
//            if (links.get(2) != null) {
//                if (link2.length() > 0 && linkName2.length() > 0) {
//                    Link linkC = linkDao.getOne(links.get(2).getId());
//                    linkC.setName(linkName2);
//                    linkC.setUrl(link2);
//                    linkC.setEcho(echo);
//                    linkDao.save(linkC);
//                }
//            }
//        }else {
//            if (link0.length() > 0 && linkName0.length() > 0) {
//                Link linkA = new Link();
//                linkA.setName(linkName0);
//                linkA.setUrl(link0);
//                linkA.setEcho(echo);
//                linkDao.save(linkA);
//            }
//
//            if (link1.length() > 0 && linkName1.length() > 0) {
//                Link linkB = new Link();
//                linkB.setName(linkName1);
//                linkB.setUrl(link1);
//                linkB.setEcho(echo);
//                linkDao.save(linkB);
//            }
//
//            if (link2.length() > 0 && linkName2.length() > 0) {
//                Link linkC = new Link();
//                linkC.setName(linkName2);
//                linkC.setUrl(link2);
//                linkC.setEcho(echo);
//                linkDao.save(linkC);
//            }
//        }
        //UPDATING LINKS
        List<Link> links = echo.getLinks();
        List<String> linkUrls = new ArrayList<>();
        if(link0.length() > 0 && !(link0.substring(0,4).equals("http"))){
            link0 = "https://" + link0;
        }
        linkUrls.add(link0);
        if(link1.length() > 0 && !(link1.substring(0,4).equals("http"))){
            link1 = "https://" + link1;
        }
        linkUrls.add(link1);
        if(link2.length() > 0 && !(link2.substring(0,4).equals("http"))){
            link2 = "https://" + link2;
        }
        linkUrls.add(link2);
        List<String> linkNames = new ArrayList<>();
        linkNames.add(linkName0);
        linkNames.add(linkName1);
        linkNames.add(linkName2);
        System.out.println(linkName0);
        System.out.println(linkName1);
        System.out.println(linkName2);
        System.out.println(links);
        System.out.println(linkUrls);
        System.out.println(linkNames);
        int count = 0;
        for(int i = 0; i < links.size(); i++) {
            count += 1;
            if (linkUrls.get(i).length() > 0 && linkNames.get(i).length() > 0) {
                    Link l = linkDao.getOne(links.get(i).getId());
                    l.setName(linkNames.get(i));
                    l.setUrl(linkUrls.get(i));
                    l.setEcho(echo);
                    linkDao.save(l);
                    System.out.println("existing link " + i + " was saved");
            }

        }
        if(count == 0){
            System.out.println("count was 0");
            if (linkUrls.get(0).length() > 0 && linkNames.get(0).length() > 0) {
                    Link l = new Link();
                    l.setName(linkNames.get(0));
                    l.setUrl(linkUrls.get(0));
                    l.setEcho(echo);
                    linkDao.save(l);
                System.out.println("count was 0 and link was saved");
            }
            count += 1;
        }
        if(count == 1){
            System.out.println("count was 1");
           if (linkUrls.get(1).length() > 0 && linkNames.get(1).length() > 0) {
                    Link l = new Link();
                    l.setName(linkNames.get(1));
                    l.setUrl(linkUrls.get(1));
                    l.setEcho(echo);
                    linkDao.save(l);
                    System.out.println("count was 1 and link was saved");
            }
            count += 1;
        }
        if(count == 2){
            System.out.println("count was 2");
            if (linkUrls.get(2).length() > 0 && linkNames.get(2).length() > 0) {
                    Link l = new Link();
                    l.setName(linkNames.get(2));
                    l.setUrl(linkUrls.get(2));
                    l.setEcho(echo);
                    linkDao.save(l);
                    System.out.println("count was 2 and link was saved");
            }
        }
        echoDao.save(echo);
        return "redirect:/echo/" + echo.getId();
    }

    @GetMapping("/echo/{id}")
    public String viewEcho(Model model, @PathVariable long id){
        Echo echo = echoDao.getOne(id);
        User user = userService.getLoggedInUser();
        List<User> commenters = new ArrayList<>();
        List<Memory> memories = echo.getMemories();
        if(memories.size() > 1){
            for(Memory m: memories){
                if(!(commenters.contains(m.getUser()))){
                    commenters.add(m.getUser());
                }
            }
            model.addAttribute("commenters", commenters);
        }
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
    public String createMemory(@ModelAttribute @Validated Memory memory,
                                Errors validation,
                                Model model,
                                @RequestParam(name = "memoryImg") MultipartFile memoryImg,
                                @RequestParam(name = "echoId") long echoId,
                                @RequestParam(name = "userId") long userId
                                ){
        if(memory.getBody() == null || memory.getBody().equals("")){
            System.out.println("Something went wrong");
            validation.rejectValue(
                    "body",
                    "memory.body",
                    "Memory must have content!"
            );
        }
        if(validation.hasErrors()){
            model.addAttribute("errors", validation);
            model.addAttribute("memory", memory);

            Echo echo = echoDao.getOne(echoId);
            User user = userService.getLoggedInUser();
            model.addAttribute("user", user);
            model.addAttribute("memory", new Memory());
            model.addAttribute("comment", new Comment());
            model.addAttribute("echo", echo);
            return "echo";
        }
        if (memoryImg != null) {
            String filename = memoryImg.getOriginalFilename();
            String filepath = Paths.get(uploadPath, filename).toString();
            File destinationFile = new File(filepath);
            try {
                memoryImg.transferTo(destinationFile);
                memory.setImage("/uploads/" + filename);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        memory.setCreatedAt(new Date());
        memory.setUser(userDao.getOne(userId));
        memory.setEcho(echoDao.getOne(echoId));
        memoryDao.save(memory);
        return "redirect:/echo/" + memory.getEcho().getId();
    }

    @PostMapping("/memory/delete")
    public String deleteMemory(@RequestParam(name="deleteMemoryId")long memoryId,
                               @RequestParam(name="memoryEchoId")long echoId){
        Memory m = memoryDao.getOne(memoryId);
        memoryDao.delete(m);
        return "redirect:/echo/" + echoId;
    }

    @PostMapping("/memory/edit")
    public String editMemory(@RequestParam(name="editMemoryId")long memoryId,
                             @RequestParam(name="memoryEchoId")long echoId,
                             @RequestParam(name="memoryImage")String memoryImage,
                             @RequestParam(name="body")String body,
                             @RequestParam(name="memoryImg")MultipartFile uploadedFile){
        Memory m = memoryDao.getOne(memoryId);
        m.setBody(body);
        m.setUpdatedAt(new Date());
        m.setImage(memoryImage);
        if(uploadedFile != null) {
            String fileName = uploadedFile.getOriginalFilename();
            String filePath = Paths.get(uploadPath, fileName).toString();
            File destinationFile = new File(filePath);
            try {
                uploadedFile.transferTo(destinationFile);
                m.setImage("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        memoryDao.save(m);
        return "redirect:/echo/" + echoId;
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

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam(name="deleteCommentId")long commentId,
                                @RequestParam(name="commentEchoId")long echoId){
        Comment c = commentDao.getOne(commentId);
        commentDao.delete(c);
        return "redirect:/echo/" + echoId;
    }

    @PostMapping("/comment/edit")
    public String editComment(@RequestParam(name="editCommentId")long commentId,
                                @RequestParam(name="commentEchoId")long echoId,
                             @RequestParam(name="body")String body){
        Comment c = commentDao.getOne(commentId);
        c.setBody(body);
        c.setUpdatedAt(new Date());
        commentDao.save(c);
        return "redirect:/echo/" + echoId;
    }


}






