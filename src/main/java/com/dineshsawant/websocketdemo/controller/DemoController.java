package com.dineshsawant.websocketdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dnsh on 25/12/17.
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    @GetMapping
    public String RedirectLogin() {
        return "redirect:/demo/login";
    }

    @GetMapping("/randomNames")
    public String randomNames() {
        return "/randomNames.html";
    }

    @GetMapping("/login")
    public String FormLogin() {
        return "/login.html";
    }

    @GetMapping("/room")
    public String Roomchat() {
        return "/roomchat.html";
    }
}
