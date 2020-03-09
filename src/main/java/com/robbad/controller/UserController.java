package com.robbad.controller;


import com.robbad.model.User;
import com.robbad.service.UserService;
import com.robbad.util.RedisUtil;
import com.robbad.util.WebTools;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;


    @GetMapping("/qdlogin")
    public String qdLogin() {
        return "qdlogin";
    }

    @RequestMapping("/loginUser")
    public String qclogin(Model model, User user, HttpServletRequest request) {
        model.addAttribute("message","用户名密码错误");
        if (StringUtils.isEmpty(user.getLgPhone())) {
            return "login";
        }
        if (StringUtils.isEmpty(user.getLgPassword())) {
            return "login";
        }

        System.out.println(user.toString());
        User returnz = userService.qclogin(user);

        try {
            if (!StringUtils.isEmpty(returnz.getId())) {
                HttpSession session = request.getSession();
                System.out.println(returnz.toString());
                session.setAttribute("lgPhone", user.getLgPhone());
                session.setAttribute("status", true);
                model.addAttribute("lgusername", returnz.getLgUsername());
                model.addAttribute("lgphone", user.getLgPhone());
            } else {
                return "login";
            }
            return "grondstoffenlijst";
        } catch (NullPointerException e) {
            System.out.println("NullPointerException---------");
            return "login";
        }

    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/register")
    public Object register(User user) {
        System.out.println(user.toString());
        if (StringUtils.isEmpty(user.getLgUsername())) return WebTools.returnData("用户名不能为空", 1);
        if (StringUtils.isEmpty(user.getLgSex())) return WebTools.returnData("性别不能为空", 1);
        if (StringUtils.isEmpty(user.getLgPhone())) return WebTools.returnData("手机号不能为空", 1);
        if (StringUtils.isEmpty(user.getLgPassword())) return WebTools.returnData("密码不能为空", 1);
//        if (!redisUtil.isCodeExist(user.getLgPhone(), user.getLgAuthcode())) return WebTools.returnData("验证码超时或错误", 1);
        return userService.userRegister(user);
    }

    /**
     * authcode验证码
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/authcode")
    public Map<String, Object> authcode(String phone) {

        if (phone.matches("^\\d{11}$") == false || phone == null) {
            return WebTools.returnData("手机号不对", 1);
        }


        return userService.authcode(phone);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/outLogin")
    public String outLogin(HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }




    @ResponseBody
    @RequestMapping("/changePassword")
    public Object changePassword(User user) {
        System.out.println(user.toString());
        if (StringUtils.isEmpty(user.getLgPhone())) return WebTools.returnData("手机号不能为空", 1);
        if (StringUtils.isEmpty(user.getLgPassword())) return WebTools.returnData("密码不能为空", 1);
//        if (!redisUtil.isCodeExist(user.getLgPhone(), user.getLgAuthcode())) return WebTools.returnData("验证码超时或错误", 1);

        return userService.changePassword(user);
    }

}
