package com.robbad.dao;

import com.robbad.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserDao {

   @Select("SELECT id,lg_username,lg_sex,lg_balance from qd_user where lg_phone=#{user.lgPhone} and lg_password=#{user.lgPassword} LIMIT 1")
   User qclogin(@Param("user") User user);

   @Insert("INSERT INTO qd_user(lg_username,lg_sex,lg_password,lg_phone) values(#{user.lgUsername},#{user.lgSex},#{user.lgPassword},#{user.lgPhone});")
   int userRegister(@Param("user") User user);

   @Select("SELECT count(*) from qd_user where lg_phone=#{phone}")
   int duplicateChecking( @Param("phone")  String phone);

   @Select("SELECT id,building from t_building_name where sex_building=#{rensex}")
   List<BuildingName> buildingNameTableList(@Param("rensex") int rensex);

    @Select("SELECT id,floor,room_number,status,owner,images,beds from t_building where building_id=#{id} GROUP BY floor")
    List<Building> floorTableLists(@Param("id") Integer id);

    @Select("SELECT id,floor,room_number,status,owner,images,beds from t_building where building_id=#{id}  and floor=#{floor}  GROUP BY room_number")
    List<Building> roomNumberTableLists(@Param("id")Integer id,@Param("floor")Integer floor);

    @Select("SELECT id,floor,room_number,status,owner,images,beds from t_building where  building_id=#{id}  and floor=#{floor} and room_number=#{roomNumber}")
    List<Building> bedsTableLists(@Param("id")Integer id, @Param("floor")Integer floor, @Param("roomNumber")Integer roomNumber);

    @Select("SELECT id,floor,room_number,building_id,status,owner,images,beds from t_building where owner=#{studentNumber} LIMIT 1")
    Building CheckingStudentNumber(@Param("studentNumber") String studentNumber);

    @Select("SELECT count(*) from t_building where status=1 and id=#{id} ")
    int CheckingStatus(@Param("id") int id);

    @Update("update t_building set owner=#{studentNumber},status=1 where id=#{id} and status=0  limit 1")
    int updateStatus(int id, String studentNumber);

    @Select("SELECT building from t_building_name where id=#{buildingId}  limit 1")
    String findbuil(@Param("buildingId") Integer buildingId);

    @Select("SELECT count(*) from qd_user where lg_phone=#{phone}  limit 1")
    int associatedChecking(@Param("phone") String phone);

    @Update("update qd_user set lg_password=#{user.lgPassword} where lg_phone=#{user.lgPhone} limit 1")
    int updatePassword(@Param("user") User user);

    List<SearchCondition> GrabASingleListImpl(@Param("searchConditions") SearchCondition searchCondition);
//    @Select(" select lg_shop_uid,qd_gm_pay,qd_fb_time,qd_sesame,qd_loanpay,qd_username,qd_region,qd_age,qd_professional,\n" +
//            "                qd_often,qd_social,qd_plicy,qd_assets,qd_income,qd_units,qd_fund,qd_time from\n" +
//            "                qd_basicmanager where lg_shop_uid=#{particularsId} limit 1")
@Select(" select * from  qd_basicmanager where lg_shop_uid=#{particularsId} limit 1")
Basicmanager particularsMessage(@Param("particularsId")Integer particularsId);

    @Insert("INSERT INTO qd_lg_power(lg_phone,lg_read,lg_addtime) values(#{lgPhone},#{particularsId},NOW());")
    int powerAdd(@Param("particularsId")Integer particularsId,@Param("lgPhone") String lgPhone);

    List<Basicmanager> purchaseList(@Param("searchConditions") SearchCondition searchCondition);

    List<Basicmanager> dicectDriveList(@Param("searchConditions")SearchCondition searchCondition);
    @Select(" select * from  qd_lg_power where lg_phone=#{searchConditions.phone} group by lg_read")
    List<QdLgPower> qdLgPowerList(@Param("searchConditions") SearchCondition searchCondition);

    @Select(" select * from  qd_lg_power where lg_phone=#{searchConditions.phone}and lg_shop_uid is not NULL  group by lg_read")
    List<QdLgPower> qdLgPowerListGM(@Param("searchConditions") SearchCondition searchCondition);

    Basicmanager GrabASingleListYidu(@Param("searchConditions") SearchCondition searchCondition,@Param("getLgRead") Integer getLgRead);



    List<Basicmanager> BasicmanagerList();
    @Select(" select * from qd_lg_power where lg_phone=#{searchConditions.phone} and lg_read = #{lgShopUid} LIMIT 1")
    QdLgPower qdLgPowerWeidu(@Param("searchConditions") SearchCondition searchCondition,@Param("lgShopUid") Integer lgShopUid);
    //查询是否联系
    @Select(" select * from qd_lg_power where lg_phone=#{searchConditions.phone} and lg_lx = #{lgShopUid} LIMIT 1")
    QdLgPower qdLgPowerWeiLianXi(@Param("searchConditions")SearchCondition searchCondition,@Param("lgShopUid") Integer lgShopUid);
    //查询是否联系
    @Select(" select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_lx = #{lgShopUid} LIMIT 1")
    Integer  whetherqdLgPowerWei(@Param("lgPhone")String lgPhone,@Param("lgShopUid") Integer lgShopUid);
    //修改余额
    @Update("update qd_user set lg_balance=lg_balance-#{price} where lg_phone=#{lgPhone} limit 1")
    Integer updateBalance(@Param("lgPhone")String lgPhone, @Param("price")Integer price);
    //添加购买信息
    @Insert("INSERT INTO qd_lg_power(lg_phone,lg_shop_uid,lg_addtime) values(#{lgPhone},#{gmid},NOW());")
    Integer addQdLgPowerGm(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //查询价格
    @Select("select qd_gm_pay from qd_basicmanager where lg_shop_uid=#{gmid}")
    Integer inquirePrice(@Param("gmid") Integer gmid);
    //查询余额
    @Select("select lg_balance from qd_user where lg_phone=#{lgPhone}")
    Integer inquireBalance(@Param("lgPhone") String lgPhone);
    //查询是否购买
    @Select("select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_shop_uid=#{gmid}")
    Integer whetherPurchase(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //查询是否购买
    @Select("select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_read=#{gmid}")
    Integer whetherYidu(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //城市名数据
    @Select("select * from qd_city")
    List<CityModel> cityInfoImp();
}
