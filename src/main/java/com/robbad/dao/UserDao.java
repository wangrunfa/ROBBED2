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

    List<Basicmanager> GrabASingleListImpl(@Param("searchConditions") SearchCondition searchCondition,@Param("privatePersonalSubscriptionsModel") PersonalSubscriptionsModel privatePersonalSubscriptionsModel);

@Select(" select * from  qd_basicmanager where lg_shop_uid=#{particularsId} limit 1")
Basicmanager particularsMessage(@Param("particularsId")Integer particularsId);

    @Insert("INSERT INTO qd_lg_power(lg_phone,lg_read,lg_addtime) values(#{lgPhone},#{particularsId},NOW());")
    int powerAdd(@Param("particularsId")Integer particularsId,@Param("lgPhone") String lgPhone);

    List<Basicmanager> purchaseList(@Param("searchConditions") SearchCondition searchCondition);

    List<Basicmanager> dicectDriveList(@Param("searchConditions")SearchCondition searchCondition,@Param("privatePersonalSubscriptionsModel")PersonalSubscriptionsModel privatePersonalZTCSubscriptionsModel);
    @Select(" select * from  qd_lg_power where lg_phone=#{searchConditions.phone} group by lg_read")
    List<QdLgPower> qdLgPowerList(@Param("searchConditions") SearchCondition searchCondition);

    @Select(" select * from  qd_lg_power where lg_phone=#{searchConditions.phone}and lg_shop_uid is not NULL  group by lg_read")
    List<QdLgPower> qdLgPowerListGM(@Param("searchConditions") SearchCondition searchCondition);

//    Basicmanager GrabASingleListYidu(@Param("searchConditions") SearchCondition searchCondition,@Param("getLgRead") Integer getLgRead);

    List<Basicmanager> BasicmanagerList();
    @Select(" select * from qd_lg_power where lg_phone=#{searchConditions.phone} and lg_read = #{lgShopUid} LIMIT 1")
    QdLgPower qdLgPowerWeidu(@Param("searchConditions") SearchCondition searchCondition,@Param("lgShopUid") Integer lgShopUid);
    //查询是否联系
    @Select(" select * from qd_lg_power where lg_phone=#{searchConditions.phone} and lg_lx != #{lgShopUid} and lg_ztc_id = #{lgShopUid} LIMIT 1")
    QdLgPower qdLgPowerWeiLianXi(@Param("searchConditions")SearchCondition searchCondition,@Param("lgShopUid") Integer lgShopUid);
    //查询是否联系
    @Select(" select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_lx = #{lgShopUid} and lg_ztc_id = #{lgShopUid} LIMIT 1")
    Integer  whetherqdLgPowerWei(@Param("lgPhone")String lgPhone,@Param("lgShopUid") Integer lgShopUid);
    //查询是否联系
    @Select(" select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_ztc_id = #{lgShopUid} LIMIT 1")
    Integer  whetherqdLgPowerWeiss(@Param("lgPhone")String lgPhone,@Param("lgShopUid") Integer lgShopUid);
    //修改余额
    @Update("update qd_userss set lg_balance=lg_balance-#{price} where lg_phone=#{lgPhone} limit 1")
    Integer updateBalance(@Param("lgPhone")String lgPhone, @Param("price")Integer price);
    //添加购买信息
    @Insert("INSERT INTO qd_lg_power(lg_phone,lg_shop_uid,lg_gm_pays,lg_addtime) values(#{lgPhone},#{gmid},#{price},NOW());")
    Integer addQdLgPowerGm(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid,@Param("price") Integer price);

    //查询价格
    @Select("select qd_jg_zhi from qd_jg_table limit 1")
    Integer inquirePricesss();

    //查询价格
    @Select("select qd_ztc_pay from qd_jg_table limit 1")
    Integer inquirePricesssztc();
    //查询余额
    @Select("select lg_balance from qd_userss where lg_phone=#{lgPhone}")
    Integer inquireBalance(@Param("lgPhone") String lgPhone);
    //查询是否购买
    @Select("select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_shop_uid=#{gmid}")
    Integer whetherPurchase(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);

    //查询是否购买 的價格
    @Select("select lg_gm_pays from qd_lg_power where lg_phone=#{lgPhone} and lg_shop_uid=#{gmid}")
    Integer whetherPurchasesss(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //查询是否已读
    @Select("select count(*) from qd_lg_power where lg_phone=#{lgPhone} and lg_read=#{gmid}")
    Integer whetherYidu(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);
    //城市名数据
    @Select("select * from qd_city")
    List<CityModel> cityInfoImp();
    //查询是否联系
    @Update("update qd_lg_power set lg_lx=#{gmid} where lg_phone=#{lgPhone} and lg_ztc_id=#{gmid}")
    Integer updateQdLgPowerContact(@Param("lgPhone")String lgPhone,@Param("gmid") Integer gmid);


    @Insert("INSERT INTO qd_straight_push_appteam(qd_sq_phone,qd_sq_order_quantity,qd_sq_team_name,qd_sq_uid,qd_sq_addtime) " +
            "values(#{straightPush.qdSqPhone},#{straightPush.qdSqOrderQuantity},#{straightPush.qdSqTeamName},#{straightPush.qdSqUid},NOW());")
    Integer directDriveApplyForImp(@Param("straightPush")StraightPush straightPush);

    @Select("select count(*) from qd_straight_push_appteam where qd_sq_uid=#{uid}")
    Integer whetherPushExcessiveDao(@Param("uid")int uid);
    @Insert("INSERT INTO qd_basicmanager(" +
            "qd_fb_time,qd_sesame,qd_wechat,qd_qq,qd_phone,qd_username,qd_loanpay," +
            "qd_region,qd_age,qd_professional,qd_often,qd_social,qd_plicy," +
            "qd_assets,qd_card,qd_marriage,qd_dress,qd_fund," +
            "qd_credit,qd_jobs,qd_units,qd_units_dress,qd_income," +
            "qd_salary,qd_submit_ip,qd_source,qd_source_name,qd_sex) " +
            "values(" +
            "NOW(),#{Basicmanager.qdSesame},#{Basicmanager.qdWechat},#{Basicmanager.qdQq},#{Basicmanager.qdPhone},#{Basicmanager.qdUsername},#{Basicmanager.qdLoanpay}" +
            ",#{Basicmanager.qdRegion},#{Basicmanager.qdAge},#{Basicmanager.qdJobs},#{Basicmanager.qdOften},#{Basicmanager.qdSocial},#{Basicmanager.qdPlicy}" +
            ",#{Basicmanager.qdAssets},#{Basicmanager.qdCard},#{Basicmanager.qdMarriage},#{Basicmanager.qdDress},#{Basicmanager.qdFund}," +
            "#{Basicmanager.qdCredit},#{Basicmanager.qdJobs},#{Basicmanager.qdUnits},#{Basicmanager.qdUnitsDress},#{Basicmanager.qdIncome}," +
            "#{Basicmanager.qdSalary},#{submitIP},#{Basicmanager.qdSource},#{Basicmanager.qdSourceName},#{Basicmanager.qdSex});")
    Integer insertBasicmanagerImpl(@Param("Basicmanager")Basicmanager basicmanager,@Param("submitIP")String submitIP);
    @Select("select count(*) from qd_basicmanager where qd_submit_ip=#{submitIP}")
    Integer findCaiLiangIP(@Param("submitIP")String submitIP);
    @Update("update qd_qdtj set qd_sql=qd_sql+1,qd_klsql=#{klsql} where qd_id=#{qdSource}")
    Integer updateQDTJSql(@Param("qdSource")int qdSource,@Param("klsql")int klsql);
    @Select("select qd_qdname from qd_qdtj where qd_id=#{qdSource}")
    String findQdSourceName(@Param("qdSource")int qdSource);
    @Select("select qd_sql,qd_klbfb from qd_qdtj where qd_id=#{qdSource}")
    QdTj findQdTj(@Param("qdSource")int qdSource);
    @Select("select count(*) from qd_xsxl where lg_phone=#{qdXsxl.lgPhone}")
    Integer findSubmitXsxl(@Param("qdXsxl")QdXsxl qdXsxl);

    @Update("update qd_xsxl set " +
            "daily_limited=#{qdXsxl.dailyLimited},push_start_time=#{qdXsxl.pushStartTime},push_stop_time=#{qdXsxl.pushStopTime}" +
            ",push_workday=#{qdXsxl.pushWorkday},push_mode=#{qdXsxl.pushMode},submit_time=NOW()" +
            " where lg_phone=#{qdXsxl.lgPhone}")
    Integer updateSubmitXsxl(@Param("qdXsxl")QdXsxl qdXsxl);
    @Insert("INSERT INTO qd_xsxl(daily_limited,push_start_time,push_stop_time,push_workday,push_mode" +
            ",lg_phone,submit_time) " +
            "values(#{qdXsxl.dailyLimited},#{qdXsxl.pushStartTime},#{qdXsxl.pushStopTime}," +
            "#{qdXsxl.pushWorkday},#{qdXsxl.pushMode},#{qdXsxl.lgPhone},NOW());")
    Integer insertSubmitXsxlImpl(@Param("qdXsxl")QdXsxl qdXsxl);
    @Select("select * from qd_xsxl where daily_limited>0 and TIMESTAMPDIFF(second,push_start_time, now())>0 and TIMESTAMPDIFF(second,push_stop_time, now())<0  and push_workday LIKE concat(concat('%',date_format(now(),'%w')),'%')  order by submit_time asc")
    List<QdXsxl> findQdXsxlLatestTime();
    @Select("select * from qd_basicmanager where qd_submit_ip=#{submitIP} and qd_phone=#{basicmanager.qdPhone}")
    Basicmanager findQdBasicmanagerOneData(@Param("basicmanager")Basicmanager basicmanager,@Param("submitIP") String submitIP);


    @Insert("INSERT INTO qd_lg_power(lg_phone,lg_shop_uid,lg_ztc_id,lg_gm_pays,lg_addtime) values(#{lgPhone},#{particularsId},#{particularsId},#{gmpays},NOW());")
    int ztcpowerAdd(@Param("particularsId")Integer particularsId,@Param("lgPhone") String lgPhone,@Param("gmpays") Integer gmpays);

    @Update("update qd_xsxl set daily_limited=daily_limited-1,submit_time=now() where xsxl_id=#{xsxlId}")
    int ztcUpdateQdXsxls(@Param("xsxlId")Integer xsxlId);

    @Update("update qd_basicmanager set qd_gshop_status=qd_gshop_status+1 where lg_shop_uid=#{LgShopUid}")
    int ztcUpdateBasicmanager(@Param("LgShopUid")Integer LgShopUid);

    @Update("update qd_basicmanager set qd_gm_pay=#{pays}")
    void updateBalancejiage(@Param("pays")Integer pays);
    @Select("select lg_biaoji from qd_lg_power where lg_ztc_id=#{particularsId} and lg_phone=#{lgPhone}")
    Object rondstoffenlijstbiaoji(@Param("particularsId")Integer particularsId, @Param("lgPhone")String lgPhone);
    @Update("update qd_lg_power set lg_biaoji=#{biaoji} where lg_phone=#{lgPhone} and lg_ztc_id=#{gmid}")
    Integer updatepowerbiaoji(@Param("gmid")Integer gmid,@Param("biaoji") String biaoji, @Param("lgPhone")String lgPhone);
    @Select("select submit_skip_url from qd_submit_skip_url limit 1")
    String  findSubmitSkipUrl();

    @Select("select submit_skip_url from qd_submit_skip_url limit 1")
    String  findztctongji();
    @Select("SELECT count(*) as zongshu,SUM(lg_gm_pays) as zongjia,lg_addtime as time,DATE_FORMAT(lg_addtime, '%m%d') as day FROM qd_lg_power  where lg_phone=#{lgPhone} and lg_ztc_id is not null GROUP BY day")
    List<ztctongjiModel> ztctongjiimpl(@Param("lgPhone") String lgPhone);

    @Select("select lg_balance from qd_userss where lg_phone=#{lgPhone}")
    Object inquirePricelgPhone(@Param("lgPhone")String lgPhone);
    @Select("select * from qd_xsxl where lg_phone=#{lgPhone} Limit 1")
    QdXsxl findxsxlsimpl(String lgPhone);

}
