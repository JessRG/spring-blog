package com.codeup.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiceController {

    @GetMapping("/roll-dice")
    public String showDiceForm() {
        return "diceResult";
    }

    @GetMapping("/roll-dice/{n}")
    public String roll(@PathVariable int n, Model model) {
        int randNum = (int) (Math.random() * 6) + 1;
        String msg = "You did not guess the right number.";
        if(n == randNum) {
            msg = "You guessed the number right!!!";
        }
        model.addAttribute("rand", randNum);
        model.addAttribute("guess", n);
        model.addAttribute("message", msg);
        model.addAttribute("title", "DiceRoll");
        return "diceResult";
    }
}
