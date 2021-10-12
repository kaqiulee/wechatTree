package com.jc.service;



import com.jc.entity.User;
import com.jc.support.AccessResult;

import java.util.List;


public interface UserService {
    AccessResult loginWithCode(String code);

    User getUserByOpenId(String openId);

//    AccessResult insertUser(User user);

    AccessResult stepInfo(String info, int userId);

    List<User> userInfo(int age);

    void toStatistic();

}
