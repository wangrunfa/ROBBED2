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

    @RequestMapping("/login")
    public String loginym1() {
        return "login";
    }
    @RequestMapping("/loginym")
    public String loginym() {
        return "login";
    }
    @RequestMapping("/DirectDriveApplyForTable")
    public String DirectDriveApplyForTable() {
        return "DirectDriveApplyForTable";
    }
    @RequestMapping("/DirectDriveNoDredge")
    public String DirectDriveNoDredge() {
        return "DirectDriveNoDredge";
    }

    @RequestMapping("/")
    public String loginym2() {
        return "login";
    }
    @RequestMapping("/changePasswordym")
    public String changePassword() {
        return "changePassword";
    }


}
