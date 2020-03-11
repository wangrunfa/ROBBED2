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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date qdFbTime;//添加时间
    private Integer qdSesame;//芝麻分
    private String qdWechat;//抢单列表中微信号
    private String qdQq;//抢单列表中qq
    private Integer qdPhone;//抢单列表中用户手机号
    private String qdUsername;//渠道用户名称
    private Integer qdLoanpay;//渠道贷款金额

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
    private String qdDepartment;//姓名与身份证是否匹配
    private String qdLiveltervals;//居住时长
    private String qdPhoneLive;//手机号在网时长
    private String qdPhoneStaus;//手机号当前状态
    private String qdCourtOrdered;//是否在法院失信名单
    private String qdDomicile;//户籍地
    private String qdUndercity;//现居地是否定位城市
    private String qdPhoneCity;//主要通话区域
    private String qdCardList;//身份证是否在信贷行业逾期名单
    private String qdLoanPhone;//贷款类号码通话情况
    private String qdNewAddress;//现居地
    private String qdUserMatching;//用户姓名与运营数据是否匹配
    private String qdHtPhone;//互通号码个数
    private String qdPhoneLoanList;//
    private String qdCsPhone;//
    private String qdJobs;//渠道用户职业
    private String qdUnits;//渠道工作单位
    private String qdUnitsDress;//渠道工作单位地址
    private String qdIncome;//渠道用户月收入
    private String qdSalary;//渠道用户发放薪水日期

    private Integer whetherContact;//是否联系
}
