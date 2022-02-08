package com.example.Demo.controllers;

import com.example.Demo.models.Message;
import com.example.Demo.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @GetMapping("/chat")
    public String messagesMain(Model model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "chat/chat_main";
    }

    @PostMapping("/chat")
    public String addMessage(
            @RequestParam String name,
            @RequestParam String text,
            Model model) {
        Message message = new Message(name, text);
        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        return "chat/chat_main";
    }
}
