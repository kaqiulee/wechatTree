package com.jc.dao;

import com.jc.entity.Step;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StepMapper {
    Step getTodayStep(int userID);
    int insertStep(@Param("userId") int userId ,@Param("step") int step);
    int updateStep(@Param("userId") int userId ,@Param("step") int step);

    List<Step> selectStep(int userId);
}
