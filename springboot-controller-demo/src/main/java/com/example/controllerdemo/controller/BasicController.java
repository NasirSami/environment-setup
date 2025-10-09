package com.example.controllerdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/greeting")
    @ResponseBody
    public String greeting() {
        return "Hello from the basic controller.";
    }
}
