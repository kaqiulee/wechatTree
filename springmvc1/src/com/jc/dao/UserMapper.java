package com.jc.dao;

import com.jc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserMapper {
    List<User> userInfo(int age);
    void toStatistic();

    User selectByOpenId(String openId);

    int updateByPrimaryKey(User user);

    int insertSelective(User user);

    User selectBySessionId(String sessionId);
}
