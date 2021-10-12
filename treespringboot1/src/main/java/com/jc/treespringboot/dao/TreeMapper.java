package com.jc.treespringboot.dao;

import com.jc.treespringboot.entity.Tree;
import com.jc.treespringboot.model.TreeMod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TreeMapper {
    int plantTree(TreeMod tree);
    Tree getTree(int treeid);
    List<Tree> getTreeByUserId(int userid);
    List<Tree> getDistanceTrees(@Param("long") double longitude , @Param("lat") double latitude);
    List<Tree> getTreeBySessionId(String sessionId);
    int updateLife(@Param("treeId") int treeId,@Param("water") int water);
    int deleteTree();
    int updateTree();
}
