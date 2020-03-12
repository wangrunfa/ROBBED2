package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class User {
    private Integer id;
    private String lgUsername;
    private String lgPassword;
    private String lgSex;
    private String lgPhone;
    private Integer lgBalance;
    private Integer lgZtcStaus;
    private String lgIp;
    private String lgAuthcode;


}
