package com.jc.service;

import com.jc.entity.Tree;
import com.jc.model.TreeMod;
import com.jc.support.AccessResult;


public interface TreeService {
    AccessResult plantTree(TreeMod tree);
    Tree getTree (int treeid);
    AccessResult stepInfo(String info, String userId);
    AccessResult getTreeByUserId(int userId);

    AccessResult getTreeBySessionId(String sessionId);

    AccessResult water(String encryptedData, String iv, String sessionId);
}
