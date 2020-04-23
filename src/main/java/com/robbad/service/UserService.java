package com.robbad.service;

import com.robbad.model.*;

import java.util.Map;

public interface UserService {

    Object userRegister(User user);

    User qclogin(User user);

    Map<String,Object> authcode(String phone);

    Map<String,Object> authcodeBasic(String phone);





    Object changePassword(User user);

    Object GrabASingleList(SearchCondition searchCondition, PersonalSubscriptionsModel privatePersonalSubscriptionsModel);

    Integer GrabASingleListzongshu(SearchCondition searchCondition, PersonalSubscriptionsModel privatePersonalSubscriptionsModel);

    Object particularsMessage(Integer particularsId, String lgPhone);

    Object purchaseList(SearchCondition searchCondition);

    Object DicectDriveList(SearchCondition searchCondition,PersonalSubscriptionsModel privatePersonalZTCSubscriptionsModel);

    Object purchaseInformation(Integer gmid,String lgPhone);

    Object cityInfo();

    Object clickContact(Integer gmid, String lgPhone);

    Object directDriveApplyForService(StraightPush straightPush);

    Integer whetherPushExcessiveDao(int uid);

    Object insertBasicmanager(Basicmanager basicmanager,String submitIP);

    Object insertSubmitXsxl(QdXsxl qdXsxl);


    Object rondstoffenlijstbiaoji(Integer particularsId, String lgPhone);

    Object updatepowerbiaoji(Integer gmid, String biaoji, String lgPhone);

    Object ztctongji(String lgPhone);

    Object findLgBalance(String lgPhone);

    Object findxsxls(String lgPhone);

    Object rondstoffenlijstbeizhu(Integer particularsId, String lgPhone);

    Object updatepowerbeizhu(Integer gmid, String beizhu, String lgPhone);

    Object findqdmessagedataajaxs(FindQdMessageModel findQdMessageModel);

    void qdMessageIp(String ipAddr,String sourceId);

    Object messageVerification(String name, String mobile, String idcard,String sourceId,String ip);

    Object findqdtjstatus(String qdSource);

    QdTj findqdmessagePage(String sourceId);

    Object insertBasicmanagergg(Basicmanager basicmanager, String ipAddr);


}
