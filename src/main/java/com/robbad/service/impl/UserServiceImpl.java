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
        List<Basicmanager> GrabASingleLists=userDao.GrabASingleListImpl(searchCondition);
        List<Basicmanager> GrabASingleArrayList=new ArrayList<>();
        for(Basicmanager GrabASingleList:GrabASingleLists){
         if(userDao.whetherPurchase(searchCondition.getPhone(),GrabASingleList.getLgShopUid())==0){
            if(searchCondition.getHaveReadUnread()==0){
               if(userDao.whetherYidu(searchCondition.getPhone(),GrabASingleList.getLgShopUid())==0){
                   GrabASingleArrayList.add(GrabASingleList);
               }
            }
            if(searchCondition.getHaveReadUnread()==1){
                if(userDao.whetherYidu(searchCondition.getPhone(),GrabASingleList.getLgShopUid())!=0){
                    GrabASingleArrayList.add(GrabASingleList);
                }
            }
            if(searchCondition.getHaveReadUnread()==2){
                    GrabASingleArrayList.add(GrabASingleList);
            }
         }
        }
        return GrabASingleArrayList;
//
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

            basicmanagerData.setQdUsername(basicmanagerData.getQdUsername().replaceAll("(?<=.{1}).*(?=.{0})","*"));
            basicmanagerData.setQdCard(StringReplaceUtil.idCardReplaceWithStar(basicmanagerData.getQdCard()));
            basicmanagerData.setQdQq("购买后显示");
            basicmanagerData.setQdPhone("购买后显示");
            basicmanagerData.setQdWechat("购买后显示");
            basicmanagerData.setQdDress(basicmanagerData.getQdDress().replaceAll("(?<=.{2}).*(?=.{0})","*"));
            basicmanagerData.setQdUnitsDress(basicmanagerData.getQdUnitsDress().replaceAll("(?<=.{2}).*(?=.{2})","*"));

            basicmanagerData.setQdUnits(basicmanagerData.getQdUnits().replaceAll("(?<=.{2}).*(?=.{2})","*"));

            return basicmanagerData;
        }
            return basicmanagerData;


    }

//    @Override
//    public Object purchaseList(SearchCondition searchCondition) {
//        List<QdLgPower> qdLgPowerList=userDao.qdLgPowerList(searchCondition);
//        List<Basicmanager> searchConditionLists=new ArrayList<>();
//        for (QdLgPower qdLgPower:qdLgPowerList) {
//
//            Basicmanager BasicmanagerData = userDao.GrabASingleListYidu(searchCondition,qdLgPower.getLgShopUid());
//            if(BasicmanagerData!=null){
//                searchConditionLists.add(BasicmanagerData);
//            }
//
//        }
//        System.out.println(searchConditionLists);
//        return searchConditionLists;
////        return userDao.purchaseList(searchCondition);
//    }

    @Override
    public Object purchaseList(SearchCondition searchCondition) {
        List<Basicmanager> purchaseLists= userDao.purchaseList(searchCondition);

        List<Basicmanager> searchConditionLists=new ArrayList<>();
        for (Basicmanager purchaseList:purchaseLists) {

            Integer getLgReadStatus = userDao.whetherPurchase(searchCondition.getPhone(),purchaseList.getLgShopUid());
            if(getLgReadStatus>0){
                searchConditionLists.add(purchaseList);
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
                    return WebTools.returnData(price,0);
                }
            }
        }

        return WebTools.returnData("购买失败",1);
    }

    @Override
    public Object cityInfo() {
        return userDao.cityInfoImp();
    }

    @Override
    public Object clickContact(Integer gmid, String lgPhone) {

                Integer addQdLgPowerContacts=userDao.addQdLgPowerContact(lgPhone,gmid);
                if (addQdLgPowerContacts>0){
                    return WebTools.returnData("成功",0);
                }


        return WebTools.returnData("失败",1);
    }

    @Override
    public Object directDriveApplyForService(StraightPush straightPush) {
        Integer whetherPushExcessive= userDao.whetherPushExcessiveDao(straightPush.getQdSqUid());
        if(whetherPushExcessive>0){
            return WebTools.returnData("你已提交过，请不要重复提交",1);
        }
        Integer returns= userDao.directDriveApplyForImp(straightPush);
        if(returns>0){
            return WebTools.returnData("提交成功",0);
        }
        return WebTools.returnData("提交失败",1);
    }

    @Override
    public Integer whetherPushExcessiveDao(int uid) {
        return userDao.whetherPushExcessiveDao(uid);
    }

    @Override
    public Object insertBasicmanager(Basicmanager basicmanager,String submitIP) {
        if(userDao.findCaiLiangIP(submitIP)>0){
            return WebTools.returnData("此IP已提交过信息，请不要用此设备再次提交",1);
        }
        if(userDao.insertBasicmanagerImpl(basicmanager,submitIP)>0){
            if(userDao.updateQDTJSql(basicmanager.getQdSource())>0){
                return WebTools.returnData("成功",0);
            }
            return WebTools.returnData("渠道来源 未知",1);
        }
        return WebTools.returnData("提交失败",1);
    }
}
