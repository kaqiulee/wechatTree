package com.jc.treespringboot.service;



import com.jc.treespringboot.entity.User;
import com.jc.treespringboot.support.AccessResult;

import java.util.List;

public interface UserService {
    AccessResult stepInfo (String info , int userId);
    List<User> userInfo(int age);

    User getUserByOpenId(String openId);

    AccessResult loginWithCode(String code);

    void toStatistic();

}
