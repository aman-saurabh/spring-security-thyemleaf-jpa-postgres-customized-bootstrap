package com.tp.sstjpcb.controller;

import com.tp.sstjpcb.model.User;
import com.tp.sstjpcb.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DefaultController {

    @Autowired
    UserRepo userRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/")
    public String home1() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user")
    public String user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Username: " + auth.getName());
        System.out.println("Roles"+auth.getAuthorities());
        return "user";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/create-user")
    public String createUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRole("USER");
        userRepo.save(user);
        return "redirect:home?signup=true";
    }

}