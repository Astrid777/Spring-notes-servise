package com.note.controllers;

import com.note.models.Post;
import com.note.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Controller
public class NoteController {

    @Autowired
    private PostRepository postRepository;

    //Добавить заметку
    @GetMapping("/note/add")
    public String noteAdd(Model model)
    {
        return "note-add";
    }

    @PostMapping("/note/add")
    public String notePostAdd(@RequestParam String title,  @RequestParam String text, Model model)
    {
        Post post = new Post(title, text);
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/note/{id}/edit")
    public String note(@PathVariable(value = "id") long id, Model model)
    {
        if(!postRepository.existsById(id)) {
            model.addAttribute("errorMessage","Поста с таким id не существует");
            return "error";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "note-edit";
    }

    @PostMapping("/note/{id}/edit")
    public String noteUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String text, Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setText(text);
        postRepository.save(post);

        return "redirect:/";
    }

    @PostMapping("/note/{id}/remove")
    public String noteDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model)
    {
        Iterable<Post> res;

        if (filter != null && !filter.isEmpty())
        {
            res = postRepository.findByTitle(filter);
        }
        else
            {
                res = postRepository.findAll();
            }

        model.put("posts", res);
        return "home";
    }
}
