package com.jc.controller;

import com.jc.entity.Tree;
import com.jc.model.TreeMod;
import com.jc.service.TreeService;
import com.jc.support.AccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tree")
public class TreeController {
    @Autowired
    private TreeService treeService;

    @PostMapping("/plantTree")
    AccessResult plantTree(TreeMod tree){
        AccessResult result = treeService.plantTree(tree);
        return result;
    }

    @PostMapping("/water1")
    AccessResult water(String encryptedData, String iv, String  sessionId){
        AccessResult result = treeService.water(encryptedData, iv, sessionId);
        return result;
    }

    @RequestMapping("/getTree")
    Tree getTree(int treeId){
        Tree tree=treeService.getTree(treeId);
        return tree;
    }

    @RequestMapping("/getTreeByUserId")
    AccessResult getTreeByUserId(int userId) {
        AccessResult result = treeService.getTreeByUserId(userId);
        return result;
    }

    @RequestMapping("/getTreeBySessionId")
    AccessResult getTreeBySessionId(String sessionId){
        AccessResult result = treeService.getTreeBySessionId(sessionId);
        return result;
    }

    @RequestMapping("/stepInfo")
    AccessResult stepInfo(String info,String userId){
//        1.设计step表，user_id,data,step
//        2.解析用户的Json运动数据
//    3.编码
        AccessResult result = treeService.stepInfo(info,userId);
        return result;
    }


}
