package com.robbad.controller;


import com.robbad.model.SearchCondition;
import com.robbad.model.StraightPush;
import com.robbad.model.User;
import com.robbad.service.UserService;
import com.robbad.util.RedisUtil;
import com.robbad.util.WebTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
    /**
     * 登录
     * @param model
     * @param request
     * @return
     */
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
                session.setAttribute("uid", returnz.getId());
                session.setAttribute("lgPhone", returnz.getLgPhone());
                session.setAttribute("lgUsername", returnz.getLgUsername());
                session.setAttribute("lgBalance", returnz.getLgBalance());
                session.setAttribute("userStatus", true);
                session.setAttribute("ztcStaus", returnz.getLgZtcStaus());
                model.addAttribute("lgUsername", returnz.getLgUsername());
                model.addAttribute("lgPhone", user.getLgPhone());
                model.addAttribute("lgBalance", returnz.getLgBalance());
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
     * 抢单列表
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/GrabASingleList")
    public Object GrabASingleList(SearchCondition searchCondition, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try{
            String phone=(String)session.getAttribute("lgPhone");
            if(phone==null){
                return WebTools.returnData("session未保持",1);
            }
            searchCondition.setPhone(phone);
            return userService.GrabASingleList(searchCondition);
        }catch (Exception e){
            return WebTools.returnData("异常",1);
        }



    }

    /**
     * 直推
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/DicectDriveList")
    public Object DicectDriveList(SearchCondition searchCondition, HttpServletRequest request) {
        HttpSession session = request.getSession();

        try{
            String phone=(String)session.getAttribute("lgPhone");
            searchCondition.setPhone(phone);
            return userService.DicectDriveList(searchCondition);
        }catch (Exception e){
            return WebTools.returnData("异常",1);
        }



    }

    /**
     * 已购客户
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/purchaseList")
    public Object purchaseList(SearchCondition searchCondition, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try{
            String phone=(String)session.getAttribute("lgPhone");
            if(phone==null){
                return WebTools.returnData("session未保持",1);
            }
            searchCondition.setPhone(phone);
            return userService.purchaseList(searchCondition);
        }catch (Exception e){
            return WebTools.returnData("异常",1);
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
      if (!redisUtil.isCodeExist(user.getLgPhone(), user.getLgAuthcode())) return WebTools.returnData("验证码超时或错误", 1);
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
     * 抢单
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/grondstoffenlijstParticularsRequest")
    public Object grondstoffenlijstParticularsRequest(Model model,Integer particularsId, HttpServletRequest request ) {
        System.out.println(particularsId);
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        model.addAttribute("particularsMessages",userService.particularsMessage(particularsId,(String)session.getAttribute("lgPhone")));
        return "Grondstoffenlijstparticulars";
    }
    /**
     * 直推车
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/DirectDriveParticularsParticularsRequest")
    public Object DirectDriveParticularsParticularsRequest(Model model,Integer particularsId, HttpServletRequest request) {
        System.out.println(particularsId);
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        model.addAttribute("particularsMessages",userService.particularsMessage(particularsId,(String)session.getAttribute("lgPhone")));

        return "DirectDriveParticulars";
    }
    /**
     * 详情页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/PurchaseParticularsParticularsRequest")
    public Object PurchaseParticularsParticularsRequest(Model model,Integer particularsId, HttpServletRequest request ) {
        System.out.println(particularsId);
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        model.addAttribute("particularsMessages",userService.particularsMessage(particularsId,(String)session.getAttribute("lgPhone")));
        return "PurchaseParticulars";
    }
    /**
     * 抢单页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/grondstoffenlijst")
    public String grondstoffenlijst(Model model, HttpServletRequest request ){
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        return "grondstoffenlijst";
    }
    /**
     * 直推 页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/DirectDrive")
    public String DirectDrive(Model model, HttpServletRequest request ){
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));

        if(userService.whetherPushExcessiveDao((int)session.getAttribute("uid"))>0 && (int)session.getAttribute("ztcStaus")==0){
            return "DirectDriveApplyForCentre";
        }
        if((int)session.getAttribute("ztcStaus")==0){
            return "DirectDriveNoDredge";
        }
        return "DirectDrive";
    }

    /**
     * 已购信息页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/purchase")
    public String purchase(Model model, HttpServletRequest request ){
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        return "purchase";
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/changePassword")
    public Object changePassword(User user) {
        System.out.println(user.toString());
        if (StringUtils.isEmpty(user.getLgPhone())) return WebTools.returnData("手机号不能为空", 1);
        if (StringUtils.isEmpty(user.getLgPassword())) return WebTools.returnData("密码不能为空", 1);
       if (!redisUtil.isCodeExist(user.getLgPhone(), user.getLgAuthcode())) return WebTools.returnData("验证码超时或错误", 1);

        return userService.changePassword(user);
    }

    /**
     * 已购信息数据
     * @param gmid
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/purchaseInformation")
    public Object purchaseInformation(Integer gmid, HttpServletRequest request ) {
        HttpSession session = request.getSession();
       Map<String,Object> maps= (Map<String, Object>) userService.purchaseInformation(gmid,(String)session.getAttribute("lgPhone"));
       if((int)maps.get("code")==0){
           session.setAttribute("lgBalance",((int)session.getAttribute("lgBalance")-(int)maps.get("data")));
       }
        return maps;
    }
    /**
     * 已购信息数据
     * @param gmid
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/clickContact")
    public Object clickContact(Integer gmid, HttpServletRequest request ) {
        HttpSession session = request.getSession();
        return userService.clickContact(gmid,(String)session.getAttribute("lgPhone"));
    }
    /**
     * 已购信息数据
     * @param gmid
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/cityInfo")
    public Object cityInfo() {
        return userService.cityInfo();
    }

    @ResponseBody
    @RequestMapping("/directDriveApplyFor")
    public Object directDriveApplyFor(StraightPush straightPush, HttpServletRequest request ) {
        HttpSession session = request.getSession();
        straightPush.setQdSqUid ((int)session.getAttribute("uid"));
        return userService.directDriveApplyForService(straightPush);
    }
}
