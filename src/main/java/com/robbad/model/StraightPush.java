package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
public class StraightPush {
    private Integer qdSqId;
    private Integer qdSqUid;
    private String qdSqPhone;
    private String qdSqOrderQuantity;
    private String qdSqTeamName;
    private Date qdSqAddtime;
}
