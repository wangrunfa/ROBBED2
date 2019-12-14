package com.robbad.service;

import com.robbad.model.User;

import java.util.Map;

public interface UserService {

    Object userRegister(User user);

    User qclogin(User user);

    Map<String,Object> authcode(String phone);

    Object buildingTableList(String sex);

    Object occupied(int id, String studentNumber);

    Object changePassword(User user);
}
