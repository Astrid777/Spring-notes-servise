package com.note.controllers;

import com.note.models.Post;
import com.note.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/")
    public String home(Model model) {

        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return "home";
    }
}