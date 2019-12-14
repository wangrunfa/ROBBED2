package com.robbad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @RequestMapping("/registerym")
    public String registerym() {
        return "register";
    }

    @RequestMapping("/loginym")
    public String loginym1() {
        return "login";
    }

    @RequestMapping("/")
    public String loginym2() {
        return "login";
    }
    @RequestMapping("/changePasswordym")
    public String changePassword() {
        return "changePassword";
    }

    @RequestMapping("/EmojiManagement")
    public String EmojiManagement(){

        return "EmojiManagement";
    }
}
