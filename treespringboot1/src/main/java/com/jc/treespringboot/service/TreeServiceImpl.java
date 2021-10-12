package com.jc.treespringboot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jc.treespringboot.dao.UserMapper;
import com.jc.treespringboot.dao.WaterMapper;
import com.jc.treespringboot.entity.Step;
import com.jc.treespringboot.entity.Tree;
import com.jc.treespringboot.entity.User;
import com.jc.treespringboot.entity.Water;
import com.jc.treespringboot.model.TreeMod;
import com.jc.treespringboot.support.config.ServerConfig;
import com.jc.treespringboot.support.utils.LocationUtils;
import com.jc.treespringboot.support.utils.WXBizDataCrypt;
import com.jc.treespringboot.dao.StepMapper;
import com.jc.treespringboot.dao.TreeMapper;
import com.jc.treespringboot.support.AccessResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TreeServiceImpl implements TreeService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TreeMapper treeMapper;
    @Autowired
    private StepMapper stepMapper;
    @Autowired
    private WaterMapper waterMapper;

    @Override
    public AccessResult plantTree(TreeMod tree) {
        User user = userMapper.selectBySessionId(tree.getSessionId());
        if (user == null) {
            return new AccessResult(-1, "无法根据sessionId找到对应用户", null);
        }

        //判断周围50米是否已经有树了
        //1. 第一点将 Tree 类和数据表tree 中的经纬度改为 double
        //2 .得到当前位置经纬度， 经度和维度分别加减 0.01 ，得到一个长方形的范围，根据这个范围查数据库
        //    获取所有的树
        //3 . 得到的所有树跟当前位置求距离，如果有小于50米的，则报错，提示你的周围已经有树，请换一个位置种树；
        //    如果没有小于50米的，则进行种树
        List<Tree> trees = treeMapper.getDistanceTrees(tree.getLongitude(), tree.getLatitude());

        for (Tree t : trees) {
            // 判断 t 和   tree 的距离是否小于50米
            double distance = LocationUtils.getDistance(t.getLatitude(), t.getLongitude(),
                    tree.getLatitude(), tree.getLongitude());

            if (distance < 50) {
                return new AccessResult(-1, "你的周围有其他树，请换一个位置种树！", null);
            }
        }

        TreeMod tree2 = new TreeMod();
        BeanUtils.copyProperties(tree, tree2);
        tree2.setUserId(user.getId());

        int result = treeMapper.plantTree(tree2); // 1, 成功   0 失败
        AccessResult accessResult = new AccessResult();
        if (result == 1) {
            accessResult.setErrcode(0); // 0 成功， -1 失败
            accessResult.setErrmsg("success plant");
        } else {
            accessResult.setErrcode(-1);
            accessResult.setErrmsg("error plant");
        }
        return accessResult;
    }

    @Override
    public Tree getTree(int treeid) {
        Tree tree = treeMapper.getTree(treeid);
        return tree;
    }

    @Override
    public AccessResult getTreeByUserId(int userid) {
        List<Tree> trees = treeMapper.getTreeByUserId(userid);
        if (trees.size() == 1) {
            Map<String, Tree> map = new HashMap<>();
            map.put("tree", trees.get(0));
            return new AccessResult(0, "查询成功", map);
        } else {
            return new AccessResult(-1, "查询失败", null);
        }
    }

    @Override
    public AccessResult getTreeBySessionId(String sessionId) {
        List<Tree> trees = treeMapper.getTreeBySessionId(sessionId);
        if (trees.size() == 1) {
            Map<String, Tree> map = new HashMap<>();
            map.put("tree", trees.get(0));
            return new AccessResult(0, "查询成功", map);
        } else {
            return new AccessResult(-1, "查询失败", null);
        }
    }


    private JSONObject decryptInfo(String encryptedData, String iv, String sessionKey) {
        String appId = ServerConfig.appID;
        WXBizDataCrypt biz = new WXBizDataCrypt(appId, sessionKey);
        String data = biz.decryptData(encryptedData, iv);
        if (data == null) {
            return null;
        }
        JSONObject jsonObj = JSON.parseObject(data);
        return jsonObj;
    }

    @Override
    public AccessResult water(String encryptedData, String iv, String sessionId) {
        //1. 根据 sessionId 得到 user对象
        //2. 解密 encryptedData 得到运动数据
        //3. 将当天的运动step 进行浇水

        User user = userMapper.selectBySessionId(sessionId);
        if (user == null) {
            return new AccessResult(-1, "无法根据sessionId找到对应用户", null);
        }

        //解码运动数据
        JSONObject jsonObj = this.decryptInfo(encryptedData, iv, user.getSessionKey());
        if (jsonObj == null) {
            return new AccessResult(-1, "解码运动数据失败", null);
        }
        List<JSONObject> stepInfoList = JSON.parseArray(jsonObj.getString("stepInfoList").toString(), JSONObject.class);

        System.out.println(stepInfoList);

        //将运动数据保存在db中
        for (JSONObject object : stepInfoList) {
            Integer timestamp = (Integer) object.get("timestamp");
            Long timestamp2 = timestamp.longValue();
            int step = (int) object.get("step");

            Date time = new Date(timestamp2 * 1000);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

            if (UserServiceImpl.isToday(sf.format(time))) {
                List<Step> steps = stepMapper.selectStep(user.getId());
                if (steps.size() > 0) {
                    stepMapper.updateStep(user.getId(), step);
                } else {
                    stepMapper.insertStep(user.getId(), step);
                }
                //使用运动步数进行浇水
                return useStepToWater(user.getId(), step);
            }
        }
        return new AccessResult(-1, "没有当天的运动步数", null);
    }

    public AccessResult useStepToWater(Integer userId, int step) {
        //获取当天已经浇水的数据
        Date now = new Date();
        List<Water> list = waterMapper.getWater(userId, now);
        int sum = 0;
        for (Water water : list) {
            sum += water.getWater();
        }

        if (step < sum) {
            return new AccessResult(-1, "运动步数错误", null);
        }

        if (step == sum) {
            return new AccessResult(-1, "你今天的步数用完了，运动后再来吧！", null);
        }

        //将这次的浇水数据保存在DB中
        List<Tree> trees = treeMapper.getTreeByUserId(userId);
        Water water = new Water();
        water.setUserId(userId);
        water.setTreeId(trees.get(0).getId());
        water.setTime(new Date());
        water.setWater(step - sum);
        waterMapper.insertWater(water);

        treeMapper.updateLife(trees.get(0).getId(), step - sum);

        Map<String, Integer> map = new HashMap<>();
        map.put("water", step - sum);
        return new AccessResult(0, "成功浇水!", map);
    }


    @Override
    public AccessResult cutTree(String seesionId) {
        List<Tree> trees = treeMapper.getTreeBySessionId(seesionId);


        if (trees.size() >= 1) {

            int lenth = trees.size();
            int i; //循环次数=长度
            for (i = 0; i <= lenth; i++) {

                if (trees.get(i).getLife() >= 5000) {

                        treeMapper.updateTree();
                        treeMapper.deleteTree();
                        return new AccessResult(0, "收获成功！请到树木收藏夹查看！", null);

                }
                else {
                    continue;
                }
            }

        }
        else {
            return new AccessResult(-1, "收获失败！没有树可以收获！", null);
        }
        return new AccessResult(-1, "text:null", null);

    }


    //查询树的收藏夹
    @Override
    public AccessResult haveTree(String sessionId) {
        List<Tree> trees = treeMapper.getTreeBySessionId(sessionId);
        if (trees.size() >= 1) {
            return new AccessResult(0, "查询成功", null);
        } else {
            return new AccessResult(-1, "您没有收获树哦！继续努力吧！", null);
        }
    }

    @Override
    public AccessResult haveTree1(String sessionId) {
        List<Tree> trees = treeMapper.getTreeBySessionId(sessionId);
        if (trees.size() >= 1) {
            return new AccessResult(0, "查询成功", null);
        } else {
            return new AccessResult(-1, "您没有收获树哦！继续努力吧！", null);
        }
    }

}
