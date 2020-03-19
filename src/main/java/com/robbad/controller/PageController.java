package com.robbad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

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

    @RequestMapping("/addmessage")
    public String addmessage() {
        return "addmessage";
    }

    @RequestMapping("/subscription")
    public String subscription() {
        return "subscription";
    }


    @RequestMapping("/ztcsubscription")
    public String ztcsubscription() {
        return "ztcsubscription";
    }
}
