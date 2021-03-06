package com.codeup.blog.controllers;

import com.codeup.blog.models.Post;
import com.codeup.blog.models.User;
import com.codeup.blog.repositories.PostRepository;
import com.codeup.blog.repositories.UserRepository;
import com.codeup.blog.services.EmailService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    // Inject post repository instance (dependency injection)
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final EmailService emailSvc;

    public PostController(PostRepository postRepo, UserRepository userRepo, EmailService emailSvc) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.emailSvc = emailSvc;
    }

    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        model.addAttribute("posts", postRepo.findAll());
        model.addAttribute("pgTitle", "All Posts");
        return "posts/index";
    }

    @GetMapping("/posts.json")
    public @ResponseBody List<Post> viewAllPostsInJSONFormat() {
        return postRepo.findAll();
    }

    @GetMapping("/posts/ajax")
    public String viewAllPostsWithAjax() {
        return "posts/ajax";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {
        Post post = postRepo.findById(id).orElse(null);
        User owner = post.getUser();
        boolean checkpw = BCrypt.checkpw("user1password", owner.getPassword());
        model.addAttribute("pgTitle", "Individual Post Page");
        model.addAttribute("post", post);
        model.addAttribute("owner", owner);
        model.addAttribute("checkpw", checkpw);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String createPostForm(Model model) {
        model.addAttribute("pgTitle", "Create Post");
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post,
                             Model model) {
//        long randomUser = (long) (Math.random() * 3) + 1;
//        User user = userRepo.findById(randomUser).orElse(null);
//        post.setUser(user);

        // Get the currently logged in user and save their ID as the author of this post
        User thisAuthor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(thisAuthor);
        if (post.getId() == 0) {
            emailSvc.prepareAndSend(post,
                    "Created Post: " + post.getTitle(),
                    post.getTitle() + "\n\n" + post.getBody());
        }
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id) {
        Post post = postRepo.findById(id).orElse(null);
        if(post != null) {
            postRepo.deleteById(post.getId());
        }
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, Model model) {
        Post post = postRepo.findById(id).orElse(null);
        if (post == null) {
            return "redirect:/posts";
        }
        model.addAttribute("pgTitle", "Edit Post");
        model.addAttribute("post", post);
        return "posts/update";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@ModelAttribute("post") Post post) {
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }
}