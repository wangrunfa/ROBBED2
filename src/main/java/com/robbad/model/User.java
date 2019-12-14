package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String studentNumber;
    private String sex;
    private String phone;
    private String authcode;
    private Date time;

}
