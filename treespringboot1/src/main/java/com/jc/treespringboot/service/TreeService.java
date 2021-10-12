package com.jc.treespringboot.service;

import com.jc.treespringboot.entity.Tree;
import com.jc.treespringboot.model.TreeMod;
import com.jc.treespringboot.support.AccessResult;


public interface TreeService {
    AccessResult plantTree(TreeMod tree);
    Tree getTree (int treeid);
    AccessResult getTreeByUserId(int userid);
    AccessResult getTreeBySessionId(String sessionId);
    AccessResult water(String encryptedData, String iv, String  sessionId);
    AccessResult cutTree(String seesionId);
    AccessResult haveTree(String sessionId);
    AccessResult haveTree1(String sessionId);
}
