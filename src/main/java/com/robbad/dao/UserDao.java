package com.robbad.dao;

import com.robbad.model.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserDao {

   @Select("SELECT * from qd_userss where lg_phone=#{user.lgPhone} and lg_password=#{user.lgPassword} LIMIT 1")
   User qclogin(@Param("user") User user);

   @Insert("INSERT INTO qd_userss(lg_username,lg_sex,lg_password,lg_phone) values(#{user.lgUsername},#{user.lgSex},#{user.lgPassword},#{user.lgPhone});")
   int userRegister(@Param("user") User user);

   @Select("SELECT count(*) from qd_userss where lg_phone=#{phone}")
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

    @Select("SELECT count(*) from qd_userss where lg_phone=#{phone}  limit 1")
    int associatedChecking(@Param("phone") String phone);

    @Update("update qd_userss set lg_password=#{user.lgPassword} where lg_phone=#{user.lgPhone} limit 1")
    int updatePassword(@Param("user") User user);

    List<Basicmanager> GrabASingleListImpl(@Param("searchConditions") SearchCondition searchCondition);

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
    @Update("update qd_userss set lg_balance=lg_balance-#{price} where lg_phone=#{lgPhone} limit 1")
    Integer updateBalance(@Param("lgPhone")String lgPhone, @Param("price")Integer price);
    //添加购买信息
    @Insert("INSERT INTO qd_lg_power(lg_phone,lg_shop_uid,lg_addtime) values(#{lgPhone},#{gmid},NOW());")
    Integer addQdLgPowerGm(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //查询价格
    @Select("select qd_gm_pay from qd_basicmanager where lg_shop_uid=#{gmid}")
    Integer inquirePrice(@Param("gmid") Integer gmid);
    //查询余额
    @Select("select lg_balance from qd_userss where lg_phone=#{lgPhone}")
    Integer inquireBalance(@Param("lgPhone") String lgPhone);
    //查询是否购买
    @Select("select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_shop_uid=#{gmid}")
    Integer whetherPurchase(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //查询是否已读
    @Select("select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_read=#{gmid}")
    Integer whetherYidu(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //城市名数据
    @Select("select * from qd_city")
    List<CityModel> cityInfoImp();
    //查询是否联系
    @Insert("INSERT INTO qd_lg_power(lg_phone,lg_lx,lg_addtime) values(#{lgPhone},#{gmid},NOW());")
    Integer addQdLgPowerContact(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);

    boolean dicectDriveQueryStatus(String phone);

    @Insert("INSERT INTO qd_straight_push_appteam(qd_sq_phone,qd_sq_order_quantity,qd_sq_team_name,qd_sq_uid,qd_sq_addtime) " +
            "values(#{straightPush.qdSqPhone},#{straightPush.qdSqOrderQuantity},#{straightPush.qdSqTeamName},#{straightPush.qdSqUid},NOW());")
    Integer directDriveApplyForImp(@Param("straightPush")StraightPush straightPush);

    @Select("select count(*) from qd_straight_push_appteam where qd_sq_uid=#{uid}")
    Integer whetherPushExcessiveDao(@Param("uid")int uid);
    @Insert("INSERT INTO qd_cailiang(" +
            "qd_fb_time,qd_sesame,qd_wechat,qd_qq,qd_phone,qd_username,qd_loanpay," +
            "qd_region,qd_age,qd_professional,qd_often,qd_social,qd_plicy," +
            "qd_assets,qd_card,qd_marriage,qd_dress,qd_fund," +
            "qd_credit,qd_jobs,qd_units,qd_units_dress,qd_income," +
            "qd_salary) " +
            "values(" +
            "NOW(),#{Basicmanager.qdSesame},#{Basicmanager.qdWechat},#{Basicmanager.qdQq},#{Basicmanager.qdPhone},#{Basicmanager.qdUsername},#{Basicmanager.qdLoanpay}" +
            ",#{Basicmanager.qdRegion},#{Basicmanager.qdAge},#{Basicmanager.qdJobs},#{Basicmanager.qdOften},#{Basicmanager.qdSocial},#{Basicmanager.qdPlicy}" +
            ",#{Basicmanager.qdAssets},#{Basicmanager.qdCard},#{Basicmanager.qdMarriage},#{Basicmanager.qdDress},#{Basicmanager.qdFund}," +
            "#{Basicmanager.qdCredit},#{Basicmanager.qdJobs},#{Basicmanager.qdUnits},#{Basicmanager.qdUnitsDress},#{Basicmanager.qdIncome}," +
            "#{Basicmanager.qdSalary});")
    Integer insertBasicmanagerImpl(@Param("Basicmanager")Basicmanager basicmanager);
}
