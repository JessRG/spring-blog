package com.codeup.blog;

import com.codeup.blog.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {

    // Inject repository instance (dependency injection)
    private PostRepository postRepo;

    public PostController(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        ArrayList<Post> posts = new ArrayList<>();

        model.addAttribute("posts", postRepo.findAll());
        model.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {
//        Post newPost = new Post();
        Post post = postRepo.findPostById(id);

        // pass post to the show page
//        model.addAttribute("post", newPost);
        model.addAttribute("pgTitle", "Individual Post");
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
