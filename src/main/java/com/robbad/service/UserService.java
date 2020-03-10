package com.robbad.service;

import com.robbad.model.SearchCondition;
import com.robbad.model.User;

import java.util.Map;

public interface UserService {

    Object userRegister(User user);

    User qclogin(User user);

    Map<String,Object> authcode(String phone);





    Object changePassword(User user);

    Object GrabASingleList(SearchCondition searchCondition);

    Object particularsMessage(Integer particularsId);
}
