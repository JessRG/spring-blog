package com.codeup.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String getLanding() {
        return "<h1>This is the landing page!</h1>";
    }
}
