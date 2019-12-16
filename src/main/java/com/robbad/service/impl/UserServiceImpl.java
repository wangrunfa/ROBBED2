package com.robbad.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.robbad.dao.UserDao;
import com.robbad.model.Building;
import com.robbad.model.BuildingName;
import com.robbad.model.User;

import com.robbad.service.UserService;
import com.robbad.util.MD5;
import com.robbad.util.RedisUtil;
import com.robbad.util.SUBMAILUtil;
import com.robbad.util.WebTools;
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
        int reint=userDao.duplicateChecking(user.getPhone(),user.getStudentNumber());
        if(reint>0){
            return WebTools.returnData("此学号或手机号已被注册！",1);
        }
        Map<String,Object> maps=getMD5(user.getPassword());
        if((int)maps.get("code")==0){
            user.setPassword((String)maps.get("data"));
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
        Map<String,Object> maps=getMD5(user.getPassword());
        if((int)maps.get("code")==0){
            user.setPassword((String)maps.get("data"));

        }else {
            System.out.println("密码加密失败!");
        }
        User reuser = userDao.qclogin(user);

        return reuser;
    }

    @Override
    public Map<String, Object> authcode(String phone) {

        if( redisUtil.isPhoneExist(phone)){
            return  WebTools.returnData("在想啥呢，小老弟！",1);
        } else {
        Map<String, Object> returnmap = SUBMAILUtil.verificationCodeSMS(phone);
        if ((int) returnmap.get("code") == 0) {
            redisUtil.setCode(phone, (String) returnmap.get("data"));

            return WebTools.returnData("成功", 0);
        }

        }
       return  WebTools.returnData("在想啥呢，小老弟！",1);
    }

    @Override
    public Object buildingTableList(String sex) {

        List list=new ArrayList();
        int rensex=1;
        if(sex.equals("男")){
            rensex=0;
        }

        //查询buildingName表
        for (BuildingName buildingNameTableLists:userDao.buildingNameTableList(rensex)) {
            //判断男女楼

            List<Object> floorInfoList=new ArrayList<>();
            //查询楼层
            List<Building> floorInfoList2=userDao.floorTableLists(buildingNameTableLists.getId());
            for (Building floorInfo:floorInfoList2) {
                Map<String,Object> roomNumberInfoMap=new HashMap<>();
                //查询房间号
                List<Building>  roomNumberInfoList=userDao.roomNumberTableLists(buildingNameTableLists.getId(),floorInfo.getFloor());
                 List<Object> roomNumber=new ArrayList<>();
                for (Building roomNumberInfo:roomNumberInfoList) {
                    //房间床位号
                    List<Building> bedsInfoList=userDao.bedsTableLists(buildingNameTableLists.getId(),floorInfo.getFloor(),roomNumberInfo.getRoomNumber());
                    Map<String,Object> bedsInfoMap=new HashMap<>();
                    bedsInfoMap.put("bedsInfoList",bedsInfoList);
                    bedsInfoMap.put("roomNumberName",roomNumberInfo.getRoomNumber());
                    roomNumber.add(bedsInfoMap);
                }
                roomNumberInfoMap.put("roomNumberList",roomNumber);
                roomNumberInfoMap.put("floorName",floorInfo.getFloor());

                floorInfoList.add(roomNumberInfoMap);
            }
//            floorInfoMap.put("roomNumberInfoList",roomNumberInfoList);
            Map<String,Object> map=new HashMap<>();
            map.put("buildingName",buildingNameTableLists.getBuilding());
            map.put("floorInfoList",floorInfoList);
            list.add(map);
        }
        return list;
    }

    @Override
    public Object occupied(int id, String studentNumber) {
        Building studentNumberStatus=userDao.CheckingStudentNumber(studentNumber);

        if(!(studentNumberStatus ==null)){

            return WebTools.returnData("此学号已有床位! <br/> "+userDao.findbuil(studentNumberStatus.getBuildingId())+"-楼层数:"+studentNumberStatus.getFloor()+"-房间号:"+studentNumberStatus.getRoomNumber()+"-床位号:"+studentNumberStatus.getBeds(),1);
        }
        int idStatus=userDao.CheckingStatus(id);
        if(idStatus>0){
            return WebTools.returnData("此床位已被先行一步！",1);
        }
        int updateStatus=userDao.updateStatus(id,studentNumber);
        if(updateStatus>0){
            Building studentNumberStatus2=userDao.CheckingStudentNumber(studentNumber);
            return WebTools.returnData("幸运之星！！！床位信息 <br/> "+userDao.findbuil(studentNumberStatus2.getBuildingId())+"-楼层数:"+studentNumberStatus2.getFloor()+"-房间号:"+studentNumberStatus2.getRoomNumber()+"-床位号:"+studentNumberStatus2.getBeds(),0);
        }

        return WebTools.returnData("抢占出现错误！",1);
    }

    @Override
    public Object changePassword(User user) {
        int reint=userDao.associatedChecking(user.getPhone(),user.getStudentNumber());

        if(reint==0){
            return WebTools.returnData("此学号和手机号没关联！",1);
        }
        Map<String,Object> maps=getMD5(user.getPassword());
        if((int)maps.get("code")==0){
            user.setPassword((String)maps.get("data"));
        }else {
            System.out.println("密码加密失败!");
        }
        int update=userDao.updatePassword(user);
        if(update>0){
            return WebTools.returnData("修改成功，请登录！",0);
        }
        return WebTools.returnData("修改失败，信息不正确",1);
    }
}
