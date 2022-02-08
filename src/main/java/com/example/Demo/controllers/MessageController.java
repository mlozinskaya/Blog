package com.example.Demo.controllers;

import com.example.Demo.models.Message;
import com.example.Demo.models.User;
import com.example.Demo.repos.MessageRepository;
import com.example.Demo.utils.DbInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    private MessageRepository messageRepository;
    private DbInitializer dbInitializer;

    @Autowired
    public MessageController(MessageRepository messageRepository, DbInitializer dbInitializer){
        this.messageRepository = messageRepository;
        this.dbInitializer = dbInitializer;
    }

    @GetMapping("/chat")
    public String messagesMain(Model model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "chat/chat_main";
    }

    @PostMapping("/chat")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            Model model) {

        if (user == null) user = dbInitializer.getAnonUser();

        Message message = new Message(user, text);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "chat/chat_main";
    }
}
