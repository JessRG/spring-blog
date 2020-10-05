package com.codeup.blog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiceController {

    @GetMapping("/roll-dice/{n}")
    public String add(@PathVariable int n, Model model) {
        int randNum = (int) (Math.random() * 6) + 1;
        String str = "You did not guess the right roll.";
        if(n == randNum) {
            str = "You guessed the roll right!!!";
        }
        model.addAttribute("rand", randNum);
        model.addAttribute("guess", n);
        model.addAttribute("result", str);
        return "dice";
    }
}
