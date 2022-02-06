package com.example.Demo.controllers;

import com.example.Demo.models.Post;
import com.example.Demo.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class BlogController {
    private PostRepository postRepository;

    @Autowired
    public BlogController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog/blog_main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog/post_add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);

        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return "blog/blog_main";
    }

    @GetMapping("/blog/{id}")
    public String blogPostDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> findResult = postRepository.findById(id);
        Post post = findResult.get();
        model.addAttribute("post", post);

        return "blog/post_details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogPostEditPage(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> findResult = postRepository.findById(id);
        Post post = findResult.get();
        model.addAttribute("post", post);

        return "blog/post_edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }
}
