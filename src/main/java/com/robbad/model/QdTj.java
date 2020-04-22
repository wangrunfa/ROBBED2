package com.robbad.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class QdTj {
    private Integer id;
    private String loginName;
    private String qdQdname;
    private String qdQdurl;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date qdAddvartime;
    private Integer qdSql;
    private String qdIp;
    private String qdId;
    private Integer qdKlbfb;
    private Integer qdKlsql;
    private Integer qdStatus;
    private Integer qdJzt;
    private Integer qdJqd;
    private String template;
}
