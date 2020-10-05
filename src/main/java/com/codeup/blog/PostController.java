package com.codeup.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String posts() {
        return "posts index page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String postsId(@PathVariable int id) {
        return String.format("view an individual post: %d", id);
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String postsCreate() {
        return "view the form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String postsCreatePost() {
        return "create a new post";
    }
}
