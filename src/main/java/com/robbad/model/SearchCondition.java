package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SearchCondition {
    private Integer haveReadUnread;//已读未读
    private String city;//地区
    private Integer rank;//排序
    private Integer number;//数量
    private String phone;//手机号



}
