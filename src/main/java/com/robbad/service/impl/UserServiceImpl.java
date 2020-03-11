package com.robbad.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.robbad.dao.UserDao;
import com.robbad.model.*;

import com.robbad.service.UserService;
import com.robbad.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.robbad.util.MD5.getMD5;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserDao userDao;

    @Override
    public Object userRegister(User user) {
        int reint=userDao.duplicateChecking(user.getLgPhone());
        if(reint>0){
            return WebTools.returnData("此手机号已被注册！",1);
        }
        Map<String,Object> maps=getMD5(user.getLgPassword());
        if((int)maps.get("code")==0){
            user.setLgPassword((String)maps.get("data"));
        }else {
            System.out.println("密码加密失败!");
        }
        int re= userDao.userRegister(user);
        if(re>0){
            return WebTools.returnData("注册成功",0);
        }

        return WebTools.returnData("注册失败",1);
    }

    @Override
    public User qclogin(User user) {
//        Map<String,Object> maps=getMD5(user.getLgPassword());
//        if((int)maps.get("code")==0){
//            user.setLgPassword((String)maps.get("data"));
//        }else {
//            System.out.println("密码加密失败!");
//        }
        User reuser = userDao.qclogin(user);

        return reuser;
    }

    @Override
    public Map<String, Object> authcode(String phone) {

        if( redisUtil.isPhoneExist(phone)){
            return  WebTools.returnData("信息请求频繁，请稍后再试",1);
        } else {
        Map<String, Object> returnmap = SmsSampleUtil.smsSample(phone);
        if ((int) returnmap.get("code") == 0) {
            redisUtil.setCode(phone, (String) returnmap.get("data"));

            return WebTools.returnData("成功", 0);
        }

        }
       return  WebTools.returnData("在想啥呢，小老弟！",1);
    }



    @Override
    public Object changePassword(User user) {
        int reint=userDao.associatedChecking(user.getLgPhone());

        if(reint==0){
            return WebTools.returnData("此手机号没注册！",1);
        }
//        Map<String,Object> maps=getMD5(user.getLgPassword());
//        if((int)maps.get("code")==0){
//            user.setLgPassword((String)maps.get("data"));
//        }else {
//            System.out.println("密码加密失败!");
//        }
        int update=userDao.updatePassword(user);
        if(update>0){
            return WebTools.returnData("修改成功，请登录！",0);
        }
        return WebTools.returnData("修改失败，信息不正确",1);
    }

    @Override
    public Object GrabASingleList(SearchCondition searchCondition) {
        if(searchCondition.getHaveReadUnread()==1){
            List<QdLgPower> qdLgPowerList=userDao.qdLgPowerList(searchCondition);
            List<Basicmanager> searchConditionLists=new ArrayList<>();
            for (QdLgPower qdLgPower:qdLgPowerList) {

                Basicmanager BasicmanagerData = userDao.GrabASingleListYidu(searchCondition,qdLgPower.getLgRead());
                if(BasicmanagerData!=null){

                    searchConditionLists.add(BasicmanagerData);
                }

            }
            System.out.println(searchConditionLists);
           return searchConditionLists;
        }
        if(searchCondition.getHaveReadUnread()==0){
            List<Basicmanager> BasicmanagerList=userDao.BasicmanagerList();
            List<Basicmanager> searchConditionLists=new ArrayList<>();
            for (Basicmanager basicmanager:BasicmanagerList) {
                QdLgPower qdLgPower = userDao.qdLgPowerWeidu(searchCondition,basicmanager.getLgShopUid());
                if(qdLgPower==null){
                    searchConditionLists.add(basicmanager);
                }
            }
            System.out.println(searchConditionLists);
            return searchConditionLists;
        }
        Object qdlist=userDao.GrabASingleListImpl(searchCondition);
        System.out.println(qdlist);
        return qdlist;
    }
    //生成很多个*号
    public String createAsterisk(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            stringBuffer.append("*");
        }
        return stringBuffer.toString();
    }

    @Override
    public Object particularsMessage(Integer particularsId, String lgPhone) {
        if(userDao.whetherYidu(lgPhone,particularsId)==0){
            userDao.powerAdd(particularsId, lgPhone);
        }
        Basicmanager basicmanagerData=userDao.particularsMessage(particularsId);
        if(userDao.whetherPurchase(lgPhone,particularsId)==0){
       // basicmanagerData.setQdCard(StringReplaceUtil.idCardReplaceWithStar(basicmanagerData.getQdCard()));
        basicmanagerData.setQdUsername(StringReplaceUtil.userNameReplaceWithStar(basicmanagerData.getQdUsername()));
        basicmanagerData.setQdCard(StringReplaceUtil.idCardReplaceWithStar(basicmanagerData.getQdCard()));



            return basicmanagerData;
        }
            return basicmanagerData;


    }

    @Override
    public Object purchaseList(SearchCondition searchCondition) {
        List<QdLgPower> qdLgPowerList=userDao.qdLgPowerList(searchCondition);
        List<Basicmanager> searchConditionLists=new ArrayList<>();
        for (QdLgPower qdLgPower:qdLgPowerList) {

            Basicmanager BasicmanagerData = userDao.GrabASingleListYidu(searchCondition,qdLgPower.getLgShopUid());
            if(BasicmanagerData!=null){
                searchConditionLists.add(BasicmanagerData);
            }

        }
        System.out.println(searchConditionLists);
        return searchConditionLists;
//        return userDao.purchaseList(searchCondition);
    }

    @Override
    public Object DicectDriveList(SearchCondition searchCondition) {
        if(searchCondition.getHaveReadUnread()==1){
            List<Basicmanager> BasicmanagerList=userDao.BasicmanagerList();
            List<Basicmanager> searchConditionLists=new ArrayList<>();
            for (Basicmanager basicmanager:BasicmanagerList) {
                QdLgPower qdLgPower = userDao.qdLgPowerWeiLianXi(searchCondition,basicmanager.getLgShopUid());
                if(qdLgPower==null){
                    basicmanager.setWhetherContact(0);
                    searchConditionLists.add(basicmanager);
                }
            }
            System.out.println(searchConditionLists);
            return searchConditionLists;
        }
        List<Basicmanager> basicmanagers = userDao.dicectDriveList(searchCondition);
        List<Basicmanager> basicmanagerList=new ArrayList<>();
        for (Basicmanager basicmanager:basicmanagers){
           if(userDao.whetherqdLgPowerWei(searchCondition.getPhone(),basicmanager.getLgShopUid())==0){
               basicmanager.setWhetherContact(0);
           }else{
               basicmanager.setWhetherContact(1);
           }
            basicmanagerList.add(basicmanager);
        }
        return basicmanagers;
    }

    @Override
    public Object purchaseInformation(Integer gmid,String lgPhone) {

        Integer price=userDao.inquirePrice(gmid);
        if(price>0){
            Integer balancec=userDao.inquireBalance(lgPhone);
            if(balancec<price){return WebTools.returnData("余额不足，请联系客服！",1);}
            Integer whetherPurchase=userDao.whetherPurchase(lgPhone,gmid);
            if(whetherPurchase>0){return WebTools.returnData("购买失败   此信息你已购买，请前往 [ 已购客户 ] 查看",1);}
            Integer returnUpdateBalance=userDao.updateBalance(lgPhone,price);
            if(returnUpdateBalance>0){
                Integer addQdLgPowerGm=userDao.addQdLgPowerGm(lgPhone,gmid);
                if (addQdLgPowerGm>0){
                    return WebTools.returnData("购买成功",0);
                }
            }
        }

        return WebTools.returnData("购买失败",1);
    }

    @Override
    public Object cityInfo() {
        return userDao.cityInfoImp();
    }
}
