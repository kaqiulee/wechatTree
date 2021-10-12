package com.jc.treespringboot.dao;


import com.jc.treespringboot.entity.Water;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface WaterMapper {
    List<Water> getWater(@Param("userId") int userId, @Param("date") Date date);
    int insertWater(Water water);
}
