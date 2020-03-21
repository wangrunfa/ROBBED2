package com.robbad.controller;


import com.robbad.model.*;
import com.robbad.service.UserService;
import com.robbad.util.MessagePostFromUtil;
import com.robbad.util.RedisUtil;
import com.robbad.util.WebTools;
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
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    //个人订阅
    private PersonalSubscriptionsModel privatePersonalSubscriptionsModel;

    //直推车订阅
    private PersonalSubscriptionsModel privatePersonalZTCSubscriptionsModel;

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
        model.addAttribute("messagephone",user.getLgPhone());
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
            return "subscription";
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
            return userService.GrabASingleList(searchCondition,privatePersonalSubscriptionsModel);
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
            return userService.DicectDriveList(searchCondition,privatePersonalZTCSubscriptionsModel);
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
        model.addAttribute("biaojineirong",userService.rondstoffenlijstbiaoji(particularsId,(String)session.getAttribute("lgPhone")));

        return "DirectDriveParticulars";
    }

    @RequestMapping("/DirectDriveApplyForTable")
    public String DirectDriveApplyForTable(Model model,Integer particularsId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        return "DirectDriveApplyForTable";
    }

    @RequestMapping("/DirectDriveNoDredge")
    public String DirectDriveNoDredge(Model model,Integer particularsId, HttpServletRequest request) {

        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        return "DirectDriveNoDredge";
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
        model.addAttribute("beizhueirong",userService.rondstoffenlijstbeizhu(particularsId,(String)session.getAttribute("lgPhone")));

        return "PurchaseParticulars";
    }
    /**
     * 详情页面
     * @param model
     * @param request
     * @return
     */

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
     * 购买生财币页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/ToBuyCurrency")
    public String ToBuyCurrency(Model model, HttpServletRequest request ){
        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));
        return "ToBuyCurrency";
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
    /**
     * 已购信息数据
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertBasicmanager")
    public Object insertBasicmanager(Basicmanager basicmanager,HttpServletRequest request) {
//        String submitIP="";
//        try {
//            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
//            InetAddress ip = null;
//            while (allNetInterfaces.hasMoreElements()) {
//                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
//                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
//                    continue;
//                } else {
//                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
//                    while (addresses.hasMoreElements()) {
//                        ip = addresses.nextElement();
//                        if (ip != null && ip instanceof Inet4Address) {
//                            submitIP= ip.getHostAddress();
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.err.println("IP地址获取失败" + e.toString());
//        }

        System.out.println(getRemortIP(request));
        System.out.println(getIpAddr(request));
         return userService.insertBasicmanager(basicmanager,getIpAddr(request));
    }
    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    /**
     * 个人订阅
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/PersonalSubscriptions")
    public Object PersonalSubscriptions(PersonalSubscriptionsModel personalSubscriptionsModelEnter) {
        privatePersonalSubscriptionsModel=personalSubscriptionsModelEnter;
        return WebTools.returnData("成功",0);
    }

    /**
     * 直推车订阅
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/PersonalZTCSubscriptions")
    public Object PersonalZTCSubscriptions(PersonalSubscriptionsModel personalSubscriptionsModelEnter) {
        privatePersonalZTCSubscriptionsModel=personalSubscriptionsModelEnter;
        return WebTools.returnData("成功",0);
    }
    /**
     *提交限时限量
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitxsxl")
    public Object submitxsxl(QdXsxl qdXsxl,HttpServletRequest request) {

        HttpSession session = request.getSession();
        qdXsxl.setLgPhone((String)session.getAttribute("lgPhone"));
        return userService.insertSubmitXsxl(qdXsxl);
    }

    /**
     *查询限时限量
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/findsubmitxsxl")
    public Object findxsxl(HttpServletRequest request) {

        HttpSession session = request.getSession();

        return userService.findxsxls((String)session.getAttribute("lgPhone"));
    }
    /**
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/submitBiaoji")
    public Object submitBiaoji(Integer gmid,String biaoji,HttpServletRequest request) {

        HttpSession session = request.getSession();

        return userService.updatepowerbiaoji(gmid,biaoji,(String)session.getAttribute("lgPhone"));
    }
    @ResponseBody
    @RequestMapping("/submitBeiZhu")
    public Object submitBeiZhu(Integer gmid,String beizhu,HttpServletRequest request) {

        HttpSession session = request.getSession();

        return userService.updatepowerbeizhu(gmid,beizhu,(String)session.getAttribute("lgPhone"));
    }
    /**
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/chongzhiannsss")
    public void chongzhiannsss(Integer gmid,String biaoji,HttpServletRequest request) {
        privatePersonalZTCSubscriptionsModel=null;
    }

    /**
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/ztctongji")
    public Object ztctongji(HttpServletRequest request) {
        HttpSession session = request.getSession();
       return userService.ztctongji((String)session.getAttribute("lgPhone"));
    }
    @RequestMapping("/DirectDriveDatajiluTable")
    public String DirectDriveDatajiluTable(HttpServletRequest request,Model model) {

        HttpSession session = request.getSession();
        model.addAttribute("lgUsername", session.getAttribute("lgUsername"));
        model.addAttribute("lgPhone", session.getAttribute("lgPhone"));
        model.addAttribute("lgBalance", session.getAttribute("lgBalance"));

        return "DirectDriveDatajiluTable";
    }

    @ResponseBody
    @RequestMapping("/findqdmessagedataajaxs")
    public Object findqdmessagedataajaxs(String loginusername,String loginpassword) {

        return userService.findqdmessagedataajaxs(loginusername,loginpassword);
    }
    @ResponseBody
    @RequestMapping("/judgeUserMessage")
    public Object judgeUserMessage(String name, String mobile, String idcard) throws IOException {

        return MessagePostFromUtil.messagePostFrom(name,mobile,idcard);
    }
}
