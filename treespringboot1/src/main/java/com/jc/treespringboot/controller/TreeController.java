package com.jc.treespringboot.controller;

import com.jc.treespringboot.entity.Tree;
import com.jc.treespringboot.model.TreeMod;
import com.jc.treespringboot.service.TreeService;
import com.jc.treespringboot.support.AccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    @RequestMapping("/getTreeByUserId")
    AccessResult getTreeByUserId(int userId){
        AccessResult result = treeService.getTreeByUserId(userId);
        return result;
    }
    @RequestMapping("/getTreeBySessionId")
    AccessResult getTreeBySessionId(String sessionId){
        AccessResult result = treeService.getTreeBySessionId(sessionId);
        return result;
    }


    @PostMapping("/water1")
    AccessResult water(String encryptedData, String iv, String  sessionId){
        AccessResult result = treeService.water(encryptedData,iv, sessionId);
        return result;
    }



    @RequestMapping("/getTree")
    Tree getTree(int treeId){
        Tree tree=treeService.getTree(treeId);
        return tree;
    }
    @RequestMapping("/cutTree")
    AccessResult cutTree(String sessionId){
        return treeService.cutTree(sessionId);
    }

    @RequestMapping("/haveTree")
    AccessResult haveTree(String sessionId){
        return treeService.haveTree(sessionId);
    }

//    @GetMapping("have Tree1")
//    public Map<String, Object> map() {
//        List<Map<String, Object>> list = (List<Map<String, Object>>) treeService.haveTree("select * from tree.uptree");
//        return list.get(6);
//    }
}
