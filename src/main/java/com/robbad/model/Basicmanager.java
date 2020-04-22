package com.robbad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ToString
@Getter
@Setter
public class Basicmanager {
    private Integer lgShopUid; //用户表购买id
    private Integer qdGmPay;//购买价格
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date qdFbTime;//添加时间
    private Integer qdSesame;//芝麻分
    private String qdWechat;//抢单列表中微信号
    private String qdQq;//抢单列表中qq
    private String qdPhone;//抢单列表中用户手机号
    private String qdUsername;//渠道用户名称

    private String qdLoanpay;//渠道贷款金额

    private String qdTime;//渠道用户显示期限
    private String qdRegion;//渠道用户地区
    private String qdAge;//渠道用户年龄
    private String qdProfessional;//渠道用户职业
    private String qdOften; //渠道学历
    private String qdSocial;//渠道用户社保
    private String qdPlicy;//渠道用户保单
    private String qdAssets;//渠道用户资产
    private String qdCard;//渠道用户身份证号
    private String qdMarriage;//渠道用户婚姻情况
    private String qdSex;//渠道用户性别
    private String qdDress;//渠道用户现住地址
    private String qdFund;//渠道用户公积金
    private String qdCredit;//渠道信用卡
    private String qdSource;//渠道用户来源
    private String qdSourceName;//渠道用户来源名字
    private String qdJobs;//渠道用户职业
    private String qdUnits;//渠道工作单位
    private String qdUnitsDress;//渠道工作单位地址
    private String qdIncome;//渠道用户月收入
    private String qdSalary;//渠道用户发放薪水日期
    private Integer qdQdztcStatus;//直推车状态
    private Integer qdGshopStatus;//购买状态
    private Integer whetherContact;//是否联系
    private String qdUserAlias;//别名
    private String LgAuthcode;//别名
}
