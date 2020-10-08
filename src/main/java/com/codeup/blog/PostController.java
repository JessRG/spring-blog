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
        model.addAttribute("posts", postRepo.findAll());
        return "posts/index";
    }

    @PostMapping("/posts")
    public String showOnePost(@RequestParam(name = "id") String id, Model model) {
        Post post = postRepo.findById(Long.parseLong(id));

        // pass post to the show page
        model.addAttribute("post", post);
        model.addAttribute("pgTitle", "Individual Post");
        return "posts/show";
    }

    @GetMapping("/posts/update")
    public String editPost(@RequestParam(name="id") String id, Model model) {
        Post editPost = postRepo.findById(Long.parseLong(id));
        model.addAttribute("editPost", editPost);
        return "posts/update";
    }

    @PostMapping("/posts/update")
    public String updatePost(@RequestParam(name = "title") String title,
                             @RequestParam(name = "body") String body,
                             @RequestParam(name = "id") String id,
                             Model model) {
        long updateId = Long.parseLong(id);
        Post updatePost = new Post(updateId, title, body);

        // update post
        postRepo.save(updatePost);
        model.addAttribute("posts", postRepo.findAll());
        return "posts/index";
    }

//    @GetMapping("/posts/create")
//    @ResponseBody
//    public String postsCreate() {
//        return "show";
//    }

//    @PostMapping("/posts/create")
//    @ResponseBody
//    public String postsCreatePost() {
//        return "create a new post";
//    }
}
