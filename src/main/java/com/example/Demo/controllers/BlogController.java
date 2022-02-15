package com.example.Demo.controllers;

import com.example.Demo.models.Post;
import com.example.Demo.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {
    private final PostRepository postRepository;

    @Autowired
    public BlogController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @ModelAttribute
    public void addPostsToModel(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
    }

    @GetMapping
    public String blogMain() {
        return "blog/blog_main";
    }

    @ModelAttribute(name = "newPost")
    public Post newPost(){
        return new Post();
    }

    @GetMapping("/add")
    public String blogAdd() {
        return "blog/post_add";
    }

    @PostMapping("/add")
    public String blogAddPost(@Valid Post post,
                              Errors errors) {
        if (errors.hasErrors()) return "redirect:/blog";

        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String blogPostDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> findResult = postRepository.findById(id);
        Post post = findResult.get();
        model.addAttribute("post", post);

        return "blog/post_details";
    }

    @GetMapping("/{id}/edit")
    public String blogPostEditPage(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)){
            return "redirect:/blog";
        }

        Optional<Post> findResult = postRepository.findById(id);
        Post post = findResult.get();
        model.addAttribute("post", post);

        return "blog/post_edit";
    }

    @PostMapping("/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @GetMapping("/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }
}
