package com.example.Demo.controllers;

import com.example.Demo.models.Role;
import com.example.Demo.models.User;
import com.example.Demo.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;

@Controller
public class AuthController {
    private UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration(){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addNewUser(User user, Model model){
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("message", "User with this name already exists");
            return "auth/registration";
        } else {
            userFromDb = new User();
        }

        userFromDb.setActive(true);
        userFromDb.setRoles(new HashSet<>(){{add(Role.GUEST);}});
        userFromDb.setUsername(user.getUsername());
        userFromDb.setPassword(user.getPassword());
        userRepository.save(userFromDb);

        return "redirect:/auth/login";
    }
}
