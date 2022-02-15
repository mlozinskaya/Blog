package com.example.Demo.controllers;

import com.example.Demo.models.Message;
import com.example.Demo.models.User;
import com.example.Demo.repos.MessageRepository;
import com.example.Demo.utils.DbInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/chat")
public class MessageController {
    private final MessageRepository messageRepository;
    private final DbInitializer dbInitializer;

    @Autowired
    public MessageController(MessageRepository messageRepository, DbInitializer dbInitializer){
        this.messageRepository = messageRepository;
        this.dbInitializer = dbInitializer;
    }

    @ModelAttribute
    public void addMessagesToModel(Model model){
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);
    }

    @GetMapping
    public String messagesMain() {
        return "chat/chat_main";
    }

    @ModelAttribute(name = "message")
    public Message newMessage(){
        return new Message();
    }

    @PostMapping
    public String addMessage(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            Errors errors) {
        if (errors.hasErrors()) return "redirect:/chat";

        if (user == null) user = dbInitializer.getAnonUser();
        message.setAuthor(user);
        messageRepository.save(message);

        return "redirect:/chat";
    }
}
