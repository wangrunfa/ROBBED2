package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PersonalSubscriptionsModel {
    private Integer minSesame;//最小芝麻分
    private Integer maxSesame;//最大芝麻分
    private Integer minAge;//最小年龄
    private Integer maxAge;//最大年龄
    private Integer minLimit;//最小额度
    private Integer maxLimit;//最大额度
    private Integer guarantee;//保单
    private Integer creditCard;//信用卡
    private Integer educationBackground;//学历
    private Integer multipleConditions;//社保公积金房车
    private String cityName;//地址
}
