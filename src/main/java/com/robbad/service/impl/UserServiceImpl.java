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
//        Map<String,Object> maps=getMD5(user.getLgPassword());
//        if((int)maps.get("code")==0){
//            user.setLgPassword((String)maps.get("data"));
//        }else {
//            System.out.println("密码加密失败!");
//        }
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
    public Object GrabASingleList(SearchCondition searchCondition,PersonalSubscriptionsModel privatePersonalSubscriptionsModel) {
        List<Basicmanager> GrabASingleLists=userDao.GrabASingleListImpl(searchCondition,privatePersonalSubscriptionsModel);
        List<Basicmanager> GrabASingleArrayList=new ArrayList<>();
        int zongshu=0;
        int NumberOfBranches=searchCondition.getNumberOfBranches();
        Integer price=userDao.inquirePricesss();
        for(Basicmanager GrabASingleList:GrabASingleLists){
            GrabASingleList.setQdGmPay(price);
         if(userDao.whetherPurchase(searchCondition.getPhone(),GrabASingleList.getLgShopUid())==0){
            if(searchCondition.getHaveReadUnread()==0){

               if(userDao.whetherYidu(searchCondition.getPhone(),GrabASingleList.getLgShopUid())==0){
                   zongshu++;
                   if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0) {
                       GrabASingleArrayList.add(GrabASingleList);
                       NumberOfBranches--;
                   }
               }
            }
            if(searchCondition.getHaveReadUnread()==1){

                if(userDao.whetherYidu(searchCondition.getPhone(),GrabASingleList.getLgShopUid())!=0){
                    zongshu++;
                    if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0) {
                    GrabASingleArrayList.add(GrabASingleList);
                        NumberOfBranches--;
                    }
                }
            }
            if(searchCondition.getHaveReadUnread()==2){
                zongshu++;
                if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0) {
                    GrabASingleArrayList.add(GrabASingleList);
                    NumberOfBranches--;
                }
            }
         }
        }

       Map<String,Object> maps=new HashMap<>();
        maps.put("zongshu",zongshu);
        maps.put("ArrayList",GrabASingleArrayList);
        return maps;
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
            Integer price=userDao.inquirePricesss();
            basicmanagerData.setQdGmPay(price);

            basicmanagerData.setQdUsername(basicmanagerData.getQdUsername().replaceAll("(?<=.{1}).*(?=.{0})","*"));
            basicmanagerData.setQdCard(StringReplaceUtil.idCardReplaceWithStar(basicmanagerData.getQdCard()));
            basicmanagerData.setQdQq("购买后显示");
            basicmanagerData.setQdSesame(0);
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
        int NumberOfBranches=searchCondition.getNumberOfBranches();
        int zongshu=0;
        List<Basicmanager> searchConditionLists=new ArrayList<>();
        for (Basicmanager purchaseList:purchaseLists) {

            Integer getLggmpay = userDao.whetherPurchasesss(searchCondition.getPhone(),purchaseList.getLgShopUid());
            if(getLggmpay!=null&&getLggmpay!=0){
                zongshu++;
                if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0){
                    purchaseList.setQdGmPay(getLggmpay);
                    searchConditionLists.add(purchaseList);
                    NumberOfBranches--;
                }

            }

        }


        Map<String,Object> maps=new HashMap<>();
        maps.put("zongshu",zongshu);
        maps.put("ArrayLists",searchConditionLists);
        return maps;
//        return userDao.purchaseList(searchCondition);
    }

    @Override
    public Object DicectDriveList(SearchCondition searchCondition,PersonalSubscriptionsModel privatePersonalZTCSubscriptionsModel) {

//        if(searchCondition.getHaveReadUnread()==1){
//            List<Basicmanager> BasicmanagerList=userDao.BasicmanagerList();
//            List<Basicmanager> searchConditionLists=new ArrayList<>();
//            for (Basicmanager basicmanager:BasicmanagerList) {
//                QdLgPower qdLgPower = userDao.qdLgPowerWeiLianXi(searchCondition,basicmanager.getLgShopUid());
//                if(qdLgPower!=null){
//                    basicmanager.setWhetherContact(0);
//                    searchConditionLists.add(basicmanager);
//                }
//            }
//            System.out.println(searchConditionLists);
//            return searchConditionLists;
//        }
        List<Basicmanager> basicmanagers = userDao.dicectDriveList(searchCondition,privatePersonalZTCSubscriptionsModel);
        int NumberOfBranches=searchCondition.getNumberOfBranches();
        int zongshu=0;
        List<Basicmanager> basicmanagerList=new ArrayList<>();
        for (Basicmanager basicmanager:basicmanagers){
           if(userDao.whetherqdLgPowerWei(searchCondition.getPhone(),basicmanager.getLgShopUid())>0){
               if(searchCondition.getHaveReadUnread()==0){
                   zongshu++;
                   if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0) {
                       basicmanager.setWhetherContact(1);
                       basicmanagerList.add(basicmanager);
                       NumberOfBranches--;
                   }
               }

           }else if(userDao.whetherqdLgPowerWeiss(searchCondition.getPhone(),basicmanager.getLgShopUid())>0){
               zongshu++;
               if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0) {
                   basicmanager.setWhetherContact(0);
                   basicmanagerList.add(basicmanager);
                   NumberOfBranches--;
               }

           }
        }

        Map<String,Object> maps=new HashMap<>();
        maps.put("zongshu",zongshu);
        maps.put("ArrayList",basicmanagerList);

        return maps;
    }

    @Override
    public Object purchaseInformation(Integer gmid,String lgPhone) {

        Integer price=userDao.inquirePricesss();
//        userDao.updateBalancejiage(userDao.inquirePricesss());
        if(price>0){
            Integer balancec=userDao.inquireBalance(lgPhone);
            if(balancec<price){return WebTools.returnData("余额不足，请联系客服！",1);}
            Integer whetherPurchase=userDao.whetherPurchase(lgPhone,gmid);
            if(whetherPurchase>0){return WebTools.returnData("购买失败   此信息你已购买，请前往 [ 已购客户 ] 查看",1);}
            Integer returnUpdateBalance=userDao.updateBalance(lgPhone,price);
            if(returnUpdateBalance>0){
                Integer addQdLgPowerGm=userDao.addQdLgPowerGm(lgPhone,gmid,price);
                if (addQdLgPowerGm>0){
                    userDao.ztcUpdateBasicmanager(gmid);
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

                Integer addQdLgPowerContacts=userDao.updateQdLgPowerContact(lgPhone,gmid);
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
        String SubmitSkipUrl=userDao.findSubmitSkipUrl();
        try{
        if(userDao.findCaiLiangIP(submitIP)>0){
//            return WebTools.returnData("此IP已提交过信息，请不要用此设备再次提交",1);
            return WebTools.returnData(SubmitSkipUrl,0);
        }
            String SourceName="云借条主渠道";
        Integer ztcgmpay=userDao.inquirePricesssztc();
        if(basicmanager.getQdSource()!=null){
            SourceName=userDao.findQdSourceName(basicmanager.getQdSource());
            QdTj QdTjTable=userDao.findQdTj(basicmanager.getQdSource());
            userDao.updateQDTJSql(basicmanager.getQdSource(),(int)(Math.floor((double)QdTjTable.getQdSql()*(double)QdTjTable.getQdKlbfb())/100));
        }



//        userDao.updateBalancejiage(ztcgmpay);
        basicmanager.setQdSourceName(SourceName);

        if(userDao.insertBasicmanagerImpl(basicmanager,submitIP)>0){

                Basicmanager basicmanagersss=userDao.findQdBasicmanagerOneData(basicmanager,submitIP);
//                QdXsxl Xsxlzs=userDao.findSubmitXsxlzs();//Xsxlzs 总数
                List<QdXsxl> QdXsxlTimeIds=userDao.findQdXsxlLatestTime();
                int status=0;
                for (QdXsxl QdXsxlTimeId:QdXsxlTimeIds) {
                    if(userDao.inquireBalance(QdXsxlTimeId.getLgPhone())>ztcgmpay){
                  if(status<(3-basicmanagersss.getQdQdztcStatus())){
                      if(userDao.updateBalance(QdXsxlTimeId.getLgPhone(),ztcgmpay)>0){
                          if (userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(), QdXsxlTimeId.getLgPhone(),ztcgmpay) > 0) {
                              if (userDao.ztcUpdateQdXsxls(QdXsxlTimeId.getXsxlId()) > 0) {
                                  if (userDao.ztcUpdateBasicmanager(basicmanagersss.getLgShopUid()) > 0) {

                                      status = status + 1;
                                  }
                                  ;

                              }
                              ;

                          }
                          ;
                      }
                  }
             }
                }

        }
        return WebTools.returnData(SubmitSkipUrl,0);
        }catch (Exception e){
            return WebTools.returnData(SubmitSkipUrl,0);
        }
    }

    @Override
    public Object insertSubmitXsxl(QdXsxl qdXsxl) {
        if(userDao.findSubmitXsxl(qdXsxl)>0){
            if(userDao.updateSubmitXsxl(qdXsxl)>0){
                return WebTools.returnData("修改成功",0);
            }
        }else{
            if(userDao.insertSubmitXsxlImpl(qdXsxl)>0){
                return WebTools.returnData("添加成功",0);
            }
        }
        return WebTools.returnData("添加失败",1);
    }

    @Override
    public Object rondstoffenlijstbiaoji(Integer particularsId, String lgPhone) {
        return userDao.rondstoffenlijstbiaoji(particularsId,lgPhone);
    }

    @Override
    public Object updatepowerbiaoji(Integer gmid, String biaoji, String lgPhone) {
        if(userDao.updatepowerbiaoji(gmid,biaoji,lgPhone)>0){
            return WebTools.returnData("成功",0);
        }
        return WebTools.returnData("失败",1);
    }
    @Override
    public Object updatepowerbeizhu(Integer gmid, String beizhu, String lgPhone) {
        if(userDao.updatepowerbeizhuimpl(gmid,beizhu,lgPhone)>0){
            return WebTools.returnData("成功",0);
        }
        return WebTools.returnData("失败",1);
    }

    @Override
    public Object findqdmessagedataajaxs(String loginusername, String loginpassword) {
        QdTj qdTj=userDao.findqdmessagedataajaxsImpl(loginusername,loginpassword);
        if(qdTj!=null){
            return WebTools.returnData(qdTj,0);
        }
        return WebTools.returnData("未查到信息",1);
    }

    @Override
    public Object ztctongji(String lgPhone) {
        return userDao.ztctongjiimpl(lgPhone);
    }

    @Override
    public Object findLgBalance(String lgPhone) {
        return userDao.inquirePricelgPhone(lgPhone);
    }

    @Override
    public Object findxsxls(String lgPhone) {
        return userDao.findxsxlsimpl(lgPhone);
    }

    @Override
    public Object rondstoffenlijstbeizhu(Integer particularsId, String lgPhone) {
        return userDao.rondstoffenlijstbeizhuImpl(particularsId,lgPhone);
    }


}
