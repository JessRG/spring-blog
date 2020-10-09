package com.codeup.blog;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    // Inject post repository instance (dependency injection)
    private PostRepository postRepo;
    private UserRepository userRepo;

    public PostController(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @GetMapping("/posts")
    public String showAllPosts(Model model) {
        model.addAttribute("posts", postRepo.findAll());
//        String hash = BCrypt.hashpw("user1password", BCrypt.gensalt());
//        System.out.println(hash);
//        hash = BCrypt.hashpw("user2password", BCrypt.gensalt());
//        System.out.println(hash);
//        hash = BCrypt.hashpw("user3password", BCrypt.gensalt());
//        System.out.println(hash);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String showOnePost(@PathVariable long id, Model model) {
        if (id == 0) {
            return "redirect:/posts";
        }
        Post post = postRepo.getPostById(id);
        User owner = post.getUser();
        boolean checkpw = BCrypt.checkpw("user1password", owner.getPassword());
        model.addAttribute("post", post);
        model.addAttribute("owner", owner);
        model.addAttribute("checkpw", checkpw);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String createPostForm() {
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(name="title") String title,
                             @RequestParam(name="body") String body,
                             Model model) {
        if (title == null || body == null) {
            return "redirect:/posts/create";
        }
        Post post = new Post();
        long randomUser = (long) (Math.random() * 3) + 1;
        User user = userRepo.getOne(randomUser);
        post.setTitle(title);
        post.setBody(body);
        post.setUser(user);
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id) {
        Post post = postRepo.getPostById(id);
        if(post != null) {
            postRepo.delete(post);
        }
        return "redirect:/posts";
    }

    @GetMapping("/posts/update/{id}")
    public String editPost(@PathVariable long id, Model model) {
        Post post = postRepo.getPostById(id);
        if (post == null) {
            return "redirect:/posts/index";
        }
        model.addAttribute("post", post);
        return "posts/update";
    }

    @PostMapping("/posts/update")
    public String updatePost(@RequestParam(name="id") long id,
                             @RequestParam(name="title") String title,
                             @RequestParam(name="body") String body,
                             Model model) {
        Post post = postRepo.getPostById(id);
        if (post == null) {
            return "redirect:/posts/index";
        }
        post.setTitle(title);
        post.setBody(body);
        postRepo.save(post);
        return "redirect:/posts/" + post.getId();
    }
}