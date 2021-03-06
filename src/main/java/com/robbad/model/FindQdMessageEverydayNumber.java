package com.robbad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@ToString
@Getter
@Setter
public class FindQdMessageEverydayNumber {
    private Integer number;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date lgAddtime;

}
