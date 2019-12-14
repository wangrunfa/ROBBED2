package com.robbad.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Building {
    private Integer id;
    private Integer floor;//楼层
    private Integer roomNumber;//房间号
    private Integer buildingId; //楼名
    private Integer sexBuilding;// 男女楼    0 男   1女
    private Integer status;//状态
    private String owner;// 拥有者
    private String images;// 图片
    private String beds;// 床铺
}
