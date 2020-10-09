package com.codeup.blog;

import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    // Inject user repository instance (dependency injection)
    private UserRepository userRepo;
}
