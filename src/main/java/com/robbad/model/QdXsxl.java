package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Time;
import java.util.Date;
@ToString
@Getter
@Setter
public class QdXsxl {
    private Integer xsxlId;
    private Integer dailyLimited;
    private Time pushStartTime;
    private Time pushStopTime;
    private String pushWorkday;
    private String pushMode;
    private String lgPhone;
    private Date submitTime;
}
