package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class QdLgPower {
    private Integer id;
    private String lgPhone;
    private Integer lgRead;
    private Integer lgLx;
    private Integer lgShopUid;
    private Integer lgZtcId;
    private Date lgAddtime;
}
