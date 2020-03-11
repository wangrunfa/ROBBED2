package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class SearchCondition {
    private Integer haveReadUnread;//已读未读
    private String city;//地区
    private Integer rank;//排序
    private Integer number;//数量
    private String phone;//手机号
    private String searchBox;//输入框
    private String startTime;//开始时间
    private String stopTime;//结束时间
    private String relation;//联系



}
