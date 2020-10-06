package com.codeup.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {

    @GetMapping("/posts")
//    @ResponseBody
    public String posts(Model model) {
        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post("First Blog Post", "This is the body of the First Post."));
        posts.add(new Post("Second Blog Post", "This is the body of the Second Post."));

        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
//    @ResponseBody
    public String postsId(@PathVariable int id, Model model) {
        Post newPost = new Post();

        newPost.setTitle("Blog Post #1");
        newPost.setBody("This is the body of First Blog.");

        // pass post to the show page
        model.addAttribute("newPost", newPost);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String postsCreate() {
        return "show";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String postsCreatePost() {
        return "create a new post";
    }
}
