package com.robbad.dao;

import com.robbad.model.Building;
import com.robbad.model.BuildingName;
import com.robbad.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserDao {

   @Select("SELECT user_id,username,sex,studentNumber from t_user where studentNumber=#{user.studentNumber} and password=#{user.password} LIMIT 1")
   User qclogin(@Param("user") User user);

   @Insert("INSERT INTO t_user(username,password,studentNumber,phone,sex,time) values(#{user.username},#{user.password},#{user.studentNumber},#{user.phone},#{user.sex},NOW());")
   int userRegister(@Param("user") User user);

   @Select("SELECT count(*) from t_user where studentNumber=#{studentNumber} or phone=#{phone}")
   int duplicateChecking( @Param("phone")  String phone, @Param("studentNumber") String studentNumber);

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

    @Select("SELECT count(*) from t_user where phone=#{phone} and studentNumber=#{studentNumber}  limit 1")
    int associatedChecking(@Param("phone") String phone,@Param("studentNumber") String studentNumber);

    @Update("update t_user set password=#{user.password} where studentNumber=#{user.studentNumber} and phone=#{user.phone} limit 1")
    int updatePassword(@Param("user") User user);
}
