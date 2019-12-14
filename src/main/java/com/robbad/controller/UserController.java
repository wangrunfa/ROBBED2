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


    @ResponseBody
    @RequestMapping("/EmojiManagementAjax")
    public Object EmojiManagementAjax(HttpServletRequest request){
        HttpSession session = request.getSession();
        try {
            String sex= (String) session.getAttribute("sex");
            return userService.buildingTableList(sex);
        }catch (NullPointerException e){
            return "错误";
        }


    }
    @RequestMapping("/loginUser")
    public String qclogin(Model model, User user, HttpServletRequest request) {
        if (StringUtils.isEmpty(user.getStudentNumber())) {
            return "login";
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return "login";
        }
        model.addAttribute("message","用户名密码错误");
        System.out.println(user.toString());
        User returnz = userService.qclogin(user);

        try {
            if (!StringUtils.isEmpty(returnz.getUsername())) {
                HttpSession session = request.getSession();
                System.out.println(returnz.toString());
                session.setAttribute("username", returnz.getUsername());
                session.setAttribute("sex", returnz.getSex());
                session.setAttribute("studentNumber", returnz.getStudentNumber());
                session.setAttribute("status", true);
                model.addAttribute("username", returnz.getUsername());
                UserController userController=new UserController();

                Object buildingTableList = userService.buildingTableList(returnz.getSex());
                model.addAttribute("buildingTableList", buildingTableList);

            } else {

                return "login";
            }
            return "EmojiManagement";
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
        if (StringUtils.isEmpty(user.getUsername())) return WebTools.returnData("用户名不能为空", 1);
        if (StringUtils.isEmpty(user.getStudentNumber())) return WebTools.returnData("学号不能为空", 1);
        if (StringUtils.isEmpty(user.getPassword())) return WebTools.returnData("密码不能为空", 1);
        if (StringUtils.isEmpty(user.getSex())) return WebTools.returnData("性别不能为空", 1);
        if (StringUtils.isEmpty(user.getPhone())) return WebTools.returnData("手机号不能为空", 1);
        if (!redisUtil.isCodeExist(user.getPhone(), user.getAuthcode())) return WebTools.returnData("验证码超时或错误", 1);

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


    /**
     * buildingTableList
     *床位详细
     * @param
     * @return
     */
    @RequestMapping("/buildingTableList")
    public String buildingTableList(){



        return "";
    }
    /**
     * buildingTableList
     *占床位
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/occupied")
    public Object occupied(int id, HttpServletRequest request){
        System.out.println(id);
        HttpSession session=request.getSession();
        if(id>0){
            System.out.println("抢占id:"+id);
        }else{
            return WebTools.returnData("验证id失败",1);
        }
        try{
            if(!(boolean) session.getAttribute("status")){
                return WebTools.returnData("验证用户失败",2);
            }

        }catch (NullPointerException e){
            return WebTools.returnData("验证用户失败",2);
        }

        String studentNumber= (String) session.getAttribute("studentNumber");
        return userService.occupied(id,studentNumber);
    }

    @ResponseBody
    @RequestMapping("/changePassword")
    public Object changePassword(User user) {
        System.out.println(user.toString());
        if (StringUtils.isEmpty(user.getStudentNumber())) return WebTools.returnData("学号不能为空", 1);
        if (StringUtils.isEmpty(user.getPassword())) return WebTools.returnData("密码不能为空", 1);
        if (StringUtils.isEmpty(user.getPhone())) return WebTools.returnData("手机号不能为空", 1);
        if (!redisUtil.isCodeExist(user.getPhone(), user.getAuthcode())) return WebTools.returnData("验证码超时或错误", 1);

        return userService.changePassword(user);
    }

}
