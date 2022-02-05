package com.example.Demo.controllers;

import com.example.Demo.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
    @GetMapping("/chat")
    public String chatMain(Model model) {
        return "chat/chat_main";
    }
}
