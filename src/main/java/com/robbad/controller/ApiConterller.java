package com.robbad.controller;

import com.robbad.model.Basicmanager;
import com.robbad.service.ApiService;
import com.robbad.util.IpUtil;
import com.robbad.util.WebTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ApiConterller {

    @Autowired
private ApiService apiService;
    /**
     * 已购信息数据
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/Api/insertBasicmanager")
    public Object apiInsertBasicmanager(Basicmanager basicmanager, HttpServletRequest request) {
        String usernameRegular="^[\\u4e00-\\u9fa5.·\\u36c3\\u4DAE]{2,}$";
        String phoneRegular = "^(1[0-9])\\d{9}$";
        String qqRegular="[1-9][0-9]{4,14}";
        String wechatRegular="^[a-zA-Z0-9_-]{5,19}$";
        String CardRegular= "^[1-9]\\d{5}[1-9]\\d{3}((0[1-9])|(1[0-2]))(([0|1|2][1-9])|3[0-1])((\\d{4})|\\d{3}X)$";
        if (StringUtils.isEmpty(basicmanager.getQdUsername())) return WebTools.returnData("用户名不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdPhone())) return WebTools.returnData("手机号不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdCard())) return WebTools.returnData("身份证号不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdQq())) return WebTools.returnData("QQ不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdWechat())) return WebTools.returnData("微信号不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdSesame())) return WebTools.returnData("芝麻分不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdAge())) return WebTools.returnData("用户年龄不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdLoanpay())) return WebTools.returnData("额度不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdRegion())) return WebTools.returnData("用户地区不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdJobs())) return WebTools.returnData("用户职业不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdOften())) return WebTools.returnData("用户学历不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdPlicy())) return WebTools.returnData("用户保单不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdSocial())) return WebTools.returnData("用户社保为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdAssets())) return WebTools.returnData("用户资产不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdMarriage())) return WebTools.returnData("用户婚姻情况不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdSex())) return WebTools.returnData("用户性别不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdDress())) return WebTools.returnData("用户现住地址不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdFund())) return WebTools.returnData("用户公积金不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdCredit())) return WebTools.returnData("信用卡不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdUnits())) return WebTools.returnData("工作单位不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdUnitsDress())) return WebTools.returnData("渠道工作单位地址不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdIncome())) return WebTools.returnData("用户月收入不能为空", 1);
        if (StringUtils.isEmpty(basicmanager.getQdSalary())) return WebTools.returnData("用户发放薪水日期不能为空", 1);
        if (!Pattern.matches(usernameRegular,basicmanager.getQdUsername()))return WebTools.returnData("用户姓名格式错误", 1);
        if (!Pattern.matches(phoneRegular,basicmanager.getQdPhone()))return WebTools.returnData("用户手机号格式错误", 1);
        if (!Pattern.matches(qqRegular,basicmanager.getQdQq()))return WebTools.returnData("用户QQ格式错误", 1);
//        if (!Pattern.matches(wechatRegular,basicmanager.getQdWechat()))return WebTools.returnData("用户微信格式错误", 1);
        if (!Pattern.matches(CardRegular,basicmanager.getQdCard()))return WebTools.returnData("用户身份证号格式错误", 1);

        return apiService.apiInsertBasicmanager(basicmanager, IpUtil.getIpAddr(request));
    }

}
