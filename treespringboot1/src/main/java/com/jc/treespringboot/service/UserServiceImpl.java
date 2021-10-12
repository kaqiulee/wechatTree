package com.jc.treespringboot.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jc.treespringboot.dao.StepMapper;
import com.jc.treespringboot.dao.UserMapper;
import com.jc.treespringboot.entity.Step;
import com.jc.treespringboot.entity.User;
import com.jc.treespringboot.model.Code2Session;
import com.jc.treespringboot.support.AccessResult;
import com.jc.treespringboot.support.config.ConstantValue;
import com.jc.treespringboot.support.config.ServerConfig;
import com.jc.treespringboot.support.config.TreeCode;
import com.jc.treespringboot.support.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service  // bean
public class UserServiceImpl implements UserService {

    // 依赖注入， 找到一个实现了 UserMapper 接口的 bean，注入到 userMapper
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StepMapper stepMapper;
    @Autowired
    private HttpUtils httpUtils;

    @Override
    public List<User> userInfo(int age){
        return userMapper.userInfo(age);
    }

    @Override
    public void toStatistic(){
        userMapper.toStatistic();
    }

    @Override
    public AccessResult stepInfo(String info, int userId) {
        List<JSONObject> list = JSONArray.parseArray(info, JSONObject.class);

        for (JSONObject object : list) {
            String date = (String) object.get("date");
            int step = (int) object.get("step");

            if (isToday(date)){
                Step s = stepMapper.getTodayStep(userId);
                if(s==null){
                    stepMapper.insertStep(userId,step);
                }else {
                    stepMapper.updateStep(userId,step);

                }
                return new AccessResult(0,"更新步数成功！",null);
            }
        }
        return null;
    }

    @Override
    public User getUserByOpenId(String openId){
        return userMapper.selectByOpenId(openId);
    }

    @Override
    public AccessResult loginWithCode(String code) {
        AccessResult result=getSessionWx(code);
        if(result.getErrcode()!=0){
            return result;
        }

        //生成 sessionid
        String openId = result.getValue().get("openid").toString();
        String sessionKey = result.getValue().get("session_key").toString();
        String sessionId = Md5(sessionKey);
        Map<String,String> map=new HashMap<>();
        map.put("sessionId",sessionId);

        //处理用户信息
        ProcessUserInfo(openId,sessionKey,sessionId);
        return new AccessResult(TreeCode.SUCCESS_CODE,TreeCode.SUCCESS_MESSAGE,map);

    }

    //如果用户不存在，则在数据库中增加用户; 如果用户存在，则更新sessionkey\sessionId
    private void ProcessUserInfo (String openId, String sessionKey, String sessionId) {
        User user = getUserByOpenId(openId);

        if(user != null){
            user.setSessionKey(sessionKey);
            user.setSessionId(sessionId);
            userMapper.updateByPrimaryKey(user);
        }else{
            user = new User();
            user.setDate(new Date());
            user.setOpenId(openId);
            user.setSessionKey(sessionKey);
            user.setSessionId(sessionId);
            userMapper.insertSelective(user);
        }
    }


    private AccessResult getSessionWx(String code){
        Code2Session codeParam = new Code2Session();
        codeParam.setAppid(ServerConfig.appID);
        codeParam.setSecret(ServerConfig.appSecret);
        codeParam.setJs_code(code);
        codeParam.setGrant_type(ConstantValue.AUTHORIZATION_CODE);
        return httpUtils.sendGetRequest(ServerConfig.code2Session, codeParam);
    }
    public String Md5(String text)
    {
        String str = DigestUtils.md5DigestAsHex(text.getBytes());
        return str;
    }

    public static boolean isToday(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String nowString = sf.format(now);

        if (date.equals(nowString)) {
            return true;
        } else {
            return false;
        }
    }
}
