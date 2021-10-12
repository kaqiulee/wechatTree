package com.jc.treespringboot.dao;

import com.jc.treespringboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    List<User> userInfo(int age);
    void toStatistic();
    int updateByPrimaryKey(User user);
    int insertSelective(User user);
    User selectByOpenId(String openId);
    User selectBySessionId(String sessionId);
}
