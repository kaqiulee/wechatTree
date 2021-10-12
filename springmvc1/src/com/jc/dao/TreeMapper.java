package com.jc.dao;

import com.jc.entity.Tree;
import com.jc.entity.User;
import com.jc.model.TreeMod;
import com.jc.support.AccessResult;
import javafx.scene.effect.Light;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface TreeMapper {
    int plantTree(TreeMod tree);
    Tree getTree(int treeid);
    List<Tree> getTreeByUserId(int userId);

    List<Tree> getTreeBySessionId(String sessionId);

//    List<Tree> getTree2(Date time);

    List<Tree> getDistantTrees(@Param("long") double longitude,@Param("lat") double latitude);


    int updateLife(@Param("treeId") int treeId,@Param("water") int water);
}
