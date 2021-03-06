package com.robbad.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.robbad.dao.UserDao;
import com.robbad.model.*;

import com.robbad.service.UserService;
import com.robbad.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public Map<String, Object> authcodeBasic(String phone) {
       Integer findPhonesReturn=userDao.findPhones(phone);
        if(findPhonesReturn!=null && findPhonesReturn > 0){
            return  WebTools.returnData("手机号已存在，请更换手机号再试",1);
        }
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
        List<Basicmanager> GrabASingleArrayListOne=new ArrayList<>();
        List<Basicmanager> GrabASingleArrayListTwo=new ArrayList<>();
        List<QdLgPower> whetherPurchaseLists = userDao.whetherPurchaseList(searchCondition.getPhone());

        int zongshu=0;

        int NumberOfBranches=searchCondition.getNumberOfBranches();
        Integer price=userDao.inquirePricesss();
        //筛选未购买 或 未直推
        for(Basicmanager GrabASingleList:GrabASingleLists){
            int jinshulianshjstatus=0;
            for (QdLgPower whetherPurchase:whetherPurchaseLists
            ) {
                //  System.out.println(GrabASingleList.getLgShopUid()+"-----"+whetherPurchase.getLgShopUid()+"-----"+whetherPurchase.getLgZtcId()+"--------"+(GrabASingleList.getLgShopUid().equals(whetherPurchase.getLgZtcId()))+"--------"+(GrabASingleList.getLgShopUid().equals(whetherPurchase.getLgShopUid())));
                if(GrabASingleList.getLgShopUid().equals(whetherPurchase.getLgZtcId()) || GrabASingleList.getLgShopUid().equals(whetherPurchase.getLgShopUid())){
                    jinshulianshjstatus++;
                    whetherPurchaseLists.remove(whetherPurchase);
                    break;
                }
            }

            if(jinshulianshjstatus==0){
                GrabASingleArrayListOne.add(GrabASingleList);
            }
        }



        //全部

        for(Basicmanager GrabASingleList:GrabASingleArrayListOne) {
            GrabASingleList.setQdGmPay(price);

            zongshu++;
            if (searchCondition.getNumberOfInitial() < zongshu && NumberOfBranches != 0) {
                GrabASingleArrayListTwo.add(GrabASingleList);
                NumberOfBranches--;
            }
        }



        Map<String,Object> maps=new HashMap<>();
        maps.put("zongshu",zongshu);
        maps.put("inquirePricesss",userDao.inquirePricesss());
        maps.put("ArrayList",GrabASingleArrayListTwo);
        return maps;
//
    }
    @Override
    public Integer GrabASingleListzongshu(SearchCondition searchCondition,PersonalSubscriptionsModel privatePersonalSubscriptionsModel) {

        Integer GrabASingleListImplzongshus=userDao.GrabASingleListImplzongshu(searchCondition,privatePersonalSubscriptionsModel);


        return  GrabASingleListImplzongshus;

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
//            basicmanagerData.setQdCard(StringReplaceUtil.idCardReplaceWithStar(basicmanagerData.getQdCard()));
//            basicmanagerData.setQdQq("购买后显示");
//            basicmanagerData.setQdSesame(0);
            basicmanagerData.setQdPhone("购买后显示");
//            basicmanagerData.setQdWechat("购买后显示");
//            basicmanagerData.setQdDress(basicmanagerData.getQdDress().replaceAll("(?<=.{2}).*(?=.{0})","*"));
//            basicmanagerData.setQdUnitsDress(basicmanagerData.getQdUnitsDress().replaceAll("(?<=.{2}).*(?=.{2})","*"));
//
//            basicmanagerData.setQdUnits(basicmanagerData.getQdUnits().replaceAll("(?<=.{2}).*(?=.{2})","*"));

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
        List<Basicmanager> basicmanagerList=new ArrayList<>();
        for (Basicmanager basicmanager:purchaseLists) {
            zongshu++;
            if (searchCondition.getNumberOfInitial() < zongshu && NumberOfBranches != 0) {

                basicmanagerList.add(basicmanager);
                NumberOfBranches--;
            }
        }
        Map<String,Object> maps=new HashMap<>();
        maps.put("zongshu",zongshu);
        maps.put("ArrayLists",basicmanagerList);
        return maps;
//        return userDao.purchaseList(searchCondition);
    }

    @Override
    public Object DicectDriveList(SearchCondition searchCondition,PersonalSubscriptionsModel privatePersonalZTCSubscriptionsModel) {

        List<Basicmanager> GrabASingleLists = userDao.dicectDriveList(searchCondition,privatePersonalZTCSubscriptionsModel);
        //Integer basicmanagerszongshu = userDao.dicectDriveListzongshu(searchCondition,privatePersonalZTCSubscriptionsModel);

        //List<Basicmanager> GrabASingleLists=userDao.GrabASingleListImpl(searchCondition,privatePersonalSubscriptionsModel);




        int NumberOfBranches=searchCondition.getNumberOfBranches();
        int zongshu=0;
        List<QdLgPower> QdLgPowerLxList=userDao.whetherqdLgPowerWeissList(searchCondition.getPhone());
        List<Basicmanager> basicmanagerList=new ArrayList<>();
        if(searchCondition.getHaveReadUnread()==0) {
            for (Basicmanager basicmanager : GrabASingleLists) {
                Integer statesCode=0;
                for (QdLgPower QdLgPowerLx : QdLgPowerLxList) {
                    if(basicmanager.getLgShopUid().equals(QdLgPowerLx.getLgLx())){
                        statesCode=1;
                        break;
                    }
                }
                if(statesCode==1){
                    basicmanager.setWhetherContact(1);
                }else{
                    basicmanager.setWhetherContact(0);
                }

                zongshu++;
                if (searchCondition.getNumberOfInitial() < zongshu && NumberOfBranches != 0) {
                    basicmanagerList.add(basicmanager);
                    NumberOfBranches--;
                }
            }
        }else{
            for (Basicmanager basicmanager : GrabASingleLists) {
                Integer statesCode=0;
                for (QdLgPower QdLgPowerLx : QdLgPowerLxList) {
                    if(basicmanager.getLgShopUid().equals(QdLgPowerLx.getLgLx())){
                        statesCode=1;
                        break;
                    }
                }
                if(statesCode==0){
                zongshu++;
                if (searchCondition.getNumberOfInitial() < zongshu && NumberOfBranches != 0) {

                        basicmanager.setWhetherContact(0);
                        basicmanagerList.add(basicmanager);
                        NumberOfBranches--;



                }
                }
            }
        }
//            if(userDao.whetherqdLgPowerWei(searchCondition.getPhone(),basicmanager.getLgShopUid())>0){
//                if(searchCondition.getHaveReadUnread()==0){
//                    zongshu++;
//                    if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0) {
//                        basicmanager.setWhetherContact(1);
//                        basicmanagerList.add(basicmanager);
//                        NumberOfBranches--;
//                    }
//
//                }
//
//            }else if(userDao.whetherqdLgPowerWeiss(searchCondition.getPhone(),basicmanager.getLgShopUid())>0){
//                zongshu++;
//                if(searchCondition.getNumberOfInitial()<zongshu&&NumberOfBranches!=0) {
//                    basicmanager.setWhetherContact(0);
//                    basicmanagerList.add(basicmanager);
//                    NumberOfBranches--;
//                }
//
//            }
//        }


        Map<String,Object> maps=new HashMap<>();
        maps.put("zongshu",zongshu);
//        maps.put("ArrayList",basicmanagerList);
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
        userDao.deleteMessage(basicmanager);
        QdTj QdTjReturn=userDao.findqdtjMessage(basicmanager.getQdSource());
        String SubmitSkipUrl=userDao.findSubmitSkipUrl();
       if(QdTjReturn.getQdStatus()==null && QdTjReturn.getQdStatus()==1) {
            return WebTools.returnData("温馨提示：渠道被冻结", 1);
        }
        try{
        if(userDao.findCaiLiangIP(submitIP)>0){
//            return WebTools.returnData("此IP已提交过信息，请不要用此设备再次提交",1);
            return WebTools.returnData(SubmitSkipUrl,2);
        }
//            String SourceName="云借条主渠道";
        Integer ztcgmpay=userDao.inquirePricesssztc();
//        if(basicmanager.getQdSource()!=null){
//            SourceName=userDao.findQdSourceName(basicmanager.getQdSource());
//            QdTj QdTjTable=userDao.findQdTj(basicmanager.getQdSource());
//            int klssqls=(int)(Math.ceil((double)(QdTjTable.getQdSql()+1)*(double)QdTjTable.getQdKlbfb()/(double)100));
//
//            System.out.println(klssqls);
//            userDao.updateQDTJSql(basicmanager.getQdSource(),klssqls);
//        }
//        userDao.updateBalancejiage(ztcgmpay);
        basicmanager.setQdSourceName(QdTjReturn.getQdQdname());

            Basicmanager basicmanagersss=null;
        if(userDao.insertBasicmanagerImpl(basicmanager,submitIP)>0){
            basicmanagersss=userDao.findQdBasicmanagerOneData(basicmanager,submitIP);


            int status=0;
            if(QdTjReturn.getQdJzt()==0){
//                QdXsxl Xsxlzs=userDao.findSubmitXsxlzs();//Xsxlzs 总数
                List<QdXsxl> QdXsxlTimeIds=userDao.findQdXsxlLatestTime();

                for (QdXsxl QdXsxlTimeId:QdXsxlTimeIds) {
                    Integer UserZtcStatus=userDao.findUserZtcStatus(QdXsxlTimeId.getLgPhone());
                    if(UserZtcStatus!=null&&UserZtcStatus==1){
                    if(userDao.inquireBalance(QdXsxlTimeId.getLgPhone())>=ztcgmpay){
                  if(status<(3-basicmanagersss.getQdQdztcStatus())){
                      if(userDao.updateBalance(QdXsxlTimeId.getLgPhone(),ztcgmpay)>0){
                          if (userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(), QdXsxlTimeId.getLgPhone(),ztcgmpay,QdTjReturn.getId()) > 0) {
                              if (userDao.ztcUpdateQdXsxls(QdXsxlTimeId.getXsxlId()) > 0) {
                                  if (userDao.ztcUpdateBasicmanager(basicmanagersss.getLgShopUid()) > 0) {
                                      status = status + 1;
                                  };
                              };
                          };
                      }
                  }
             }
                    }
                }

            }
            if(status==0){
                userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(),"",0,QdTjReturn.getId());
            }
        }
            if(QdTjReturn.getQdJqd()==1) {
                userDao.addQdJztStatus(basicmanagersss.getLgShopUid(),QdTjReturn.getId());
            }
        return WebTools.returnData(SubmitSkipUrl,0);
        }catch (Exception e){
            return WebTools.returnData(SubmitSkipUrl,2);
        }
    }



    @Override
    public Object insertBasicmanagergg(Basicmanager basicmanager,String submitIP,Integer statusBs) {
        if(statusBs==0){
            userDao.deleteMessage(basicmanager);
        }

        QdTj QdTjReturn=userDao.findqdtjMessage(basicmanager.getQdSource());
        String SubmitSkipUrl=userDao.findSubmitSkipUrl();
        if(QdTjReturn.getQdStatus()==null && QdTjReturn.getQdStatus()==1) {
            return WebTools.returnData("温馨提示：渠道被冻结", 1);
        }
        if (!(Integer.parseInt(basicmanager.getQdAge())>=23&&Integer.parseInt(basicmanager.getQdAge())<=50)){
            return WebTools.returnData(SubmitSkipUrl,0);
        }
        try{
            if(userDao.findCaiLiangIP(submitIP)>0){
                return WebTools.returnData(SubmitSkipUrl,2);
            }
            Integer ztcgmpay=userDao.inquirePricesssztc();
            basicmanager.setQdSourceName(QdTjReturn.getQdQdname());

            Basicmanager basicmanagersss=null;

            if(userDao.insertBasicmanagerggImpl(basicmanager,submitIP)>0){
                basicmanagersss=userDao.findQdBasicmanagerOneData(basicmanager,submitIP);
                int status=0;
                if(QdTjReturn.getQdJzt()==0){
                    List<QdXsxl> QdXsxlTimeIds=userDao.findQdXsxlLatestTime();
                    for (QdXsxl QdXsxlTimeId:QdXsxlTimeIds) {
                        Integer UserZtcStatus=userDao.findUserZtcStatus(QdXsxlTimeId.getLgPhone());
                        if(UserZtcStatus!=null&&UserZtcStatus==1){
                            if(userDao.inquireBalance(QdXsxlTimeId.getLgPhone())>=ztcgmpay){
                                if(status<(3-basicmanagersss.getQdQdztcStatus())){
                                    if(userDao.updateBalance(QdXsxlTimeId.getLgPhone(),ztcgmpay)>0){
                                        if (userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(), QdXsxlTimeId.getLgPhone(),ztcgmpay,QdTjReturn.getId()) > 0) {
                                            if (userDao.ztcUpdateQdXsxls(QdXsxlTimeId.getXsxlId()) > 0) {
                                                if (userDao.ztcUpdateBasicmanager(basicmanagersss.getLgShopUid()) > 0) {
                                                    status = status + 1;
                                                };
                                            };
                                        };
                                    }
                                }
                            }
                        }
                    }

                }
                if(status==0){
                    userDao.ztcpowerAdd(basicmanagersss.getLgShopUid(),"",0,QdTjReturn.getId());
                }
            }
            if(QdTjReturn.getQdJqd()==1) {
                userDao.addQdJztStatus(basicmanagersss.getLgShopUid(),QdTjReturn.getId());
            }
            return WebTools.returnData(SubmitSkipUrl,0);
        }catch (Exception e){
            return WebTools.returnData(SubmitSkipUrl,2);
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
    public Object findqdmessagedataajaxs(FindQdMessageModel findQdMessageModel) {
        QdTj qdTj=userDao.findqdmessagedataajaxsImpl(findQdMessageModel);

        if(qdTj!=null){
            Map<String,Object> maps=new HashMap<>();

            //findqdmessageEverydayNumberImpl 查询每日申请数量
            List<FindQdMessageEverydayNumber> lists=new ArrayList<>();
            List<FindQdMessageEverydayNumber> findQdMessageEverydayNumberList=userDao.findqdmessageEverydayNumberImpl(findQdMessageModel,qdTj.getId());
            for (FindQdMessageEverydayNumber findQdMessageEverydayNumber:findQdMessageEverydayNumberList ) {
                findQdMessageEverydayNumber.setNumber((int)(Math.ceil((double)findQdMessageEverydayNumber.getNumber()+1)*(double)qdTj.getQdKlbfb()/(double)100));

                lists.add(findQdMessageEverydayNumber);
            }
            maps.put("qdTj",qdTj);
            qdTj.setQdKlbfb(0);
            maps.put("EverydayNumberList",lists);
            int zongshus=0;
            List<FindQdMessageEverydayNumber> FindQdMessageNumberList=userDao.findqdmessageNumberImpl(findQdMessageModel,qdTj.getId());
            for (FindQdMessageEverydayNumber findQdMessageNumber:FindQdMessageNumberList) {
                zongshus++;
            }
            maps.put("zongshus",zongshus);
            return WebTools.returnData(maps,0);
        }
        return WebTools.returnData("未查到信息",1);
    }

    @Override
    public void qdMessageIp(String ipAddr,String sourceId) {
        QdTj qdTj=userDao.findQdTjId(sourceId);
       if(qdTj!=null){
           if(userDao.findQdSqIp(ipAddr,qdTj.getId())>0 && userDao.findQdPvUv(qdTj.getId())>0){

                   userDao.addQdTjPvNumber(qdTj.getId());
           }else{
                   userDao.addQdSqIp(ipAddr, qdTj.getId());
                   userDao.addQdTjPvUv(qdTj.getId());
                   userDao.addQdTjPvUvNumber(qdTj.getId());

           }
       };
    }

    @Override
    public Object messageVerification(String name, String mobile, String idcard,String sourceId,String ip) {
        QdTj SourceStatus=userDao.findqdtjstatus(sourceId);
        if(SourceStatus==null && SourceStatus.getQdStatus()==1) {
            return WebTools.returnData("温馨提示：渠道被冻结", 1);
        }
        Integer findQdNumber=userDao.findQdMessageVerify(mobile,idcard);
        if(findQdNumber!=null && findQdNumber>=5){
//            return WebTools.returnData("错误提示!此用户信息验证已达上限",1);
            return WebTools.returnData("错误提示!实名认证已被锁，请勿重复申请",1);
        }
        if(findQdNumber==null){
            userDao.addQdMessageVerify(mobile,idcard,name,ip,SourceStatus.getId());
            findQdNumber=0;
        }
        Integer findExists=userDao.findQdBasicmanager(mobile,idcard);
        if(findExists>0){
            userDao.updateMessageStatus(mobile,idcard,2);
            return WebTools.returnData("错误提示!身份证或手机号已被申请，请不要重复使用手机号，身份证申请",1);
        }
        Integer findIpNumber=userDao.findIp(ip);
        if(findIpNumber!=null && findIpNumber>=5){
//           return WebTools.returnData("错误提示!此IP验证已达上限",1);

            return WebTools.returnData("错误提示!实名认证已被锁，请勿重复申请",1);
        }
        if(findIpNumber==null){
           userDao.addIp(ip);
            findIpNumber=0;
        }

        try {
            userDao.updateIpNumber(ip);
        userDao.updateQdNumber(mobile,idcard);
                JSONObject ObjectMessage=  MessagePostFromUtil.messagePostFrom(name,mobile,idcard);
                if(ObjectMessage.getInteger("code")==0){

             String birthdaysq=ObjectMessage.getJSONObject("result").getString("birthday");
             if(birthdaysq!=null&&birthdaysq!="null") {
                String birthdaysqdatas=(birthdaysq.substring(0, 4) + "-" + birthdaysq.substring(4, 6) + "-" + birthdaysq.substring(6, 8));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateTime = null;
                try {
                    dateTime = simpleDateFormat.parse(birthdaysqdatas);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Integer age=getAgeByBirth(dateTime);
                if(age<23 || age>45){
                    userDao.updateMessageStatus(mobile,idcard,3);
                }
             }else{
                 userDao.updateMessageStatus(mobile,idcard,1);
             }
                  ObjectMessage.put("requsetNumber",WebTools.takeTheMaximum(findQdNumber,findIpNumber));
                  return ObjectMessage;
                }else{
                    userDao.updateMessageStatus(mobile,idcard,1);
                    return WebTools.returnData("温馨提示!信息填写错误",1);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userDao.updateMessageStatus(mobile,idcard,1);
        return WebTools.returnData("温馨提示!身份验证失败",1);
    }
    public static int getAgeByBirth(Date birthDay) throws ParseException {
        int age = 0;
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    @Override
    public Object findqdtjstatus(String qdSource) {
        QdTj sss=userDao.findqdtjstatus(qdSource);
        if(sss==null){
            return WebTools.returnData("未找到 - "+qdSource+" - 渠道",2);
        }
        if(sss.getQdStatus()==0){
          return WebTools.returnData(qdSource+" - 渠道可用",0);
        }
        if(sss.getQdStatus()==1){
            return WebTools.returnData("渠道 - "+qdSource+" - 已被冻结", 1);
        }
        return WebTools.returnData("未找到 - "+qdSource+" - 渠道",2);
    }

    @Override
    public QdTj findqdmessagePage(String sourceId) {

        return  userDao.findqdmessagePagempl(sourceId);
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
