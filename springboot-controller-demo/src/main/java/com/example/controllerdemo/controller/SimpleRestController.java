package com.example.controllerdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/simple")
public class SimpleRestController {

    @GetMapping("/echo")
    public String echo(@RequestParam(defaultValue = "Hello") String message) {
        return "Echo: " + message;
    }
}
