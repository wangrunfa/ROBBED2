package com.robbad.model;

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
    private Date qdAddvartime;
    private Integer qdSql;
    private String qdIp;
    private Integer qdId;
    private Integer qdKlbfb;
    private Integer qdKlsql;
}
