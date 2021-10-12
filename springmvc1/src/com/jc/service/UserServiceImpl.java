package com.jc.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jc.dao.StepMapper;
import com.jc.dao.UserMapper;
import com.jc.entity.Step;
import com.jc.entity.User;
import com.jc.model.Code2Session;
import com.jc.support.AccessResult;
import com.jc.support.config.ConstantValue;
import com.jc.support.config.ServerConfig;
import com.jc.support.config.TreeCode;
import com.jc.support.utils.HttpUtils;
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
    // 依赖注入， 找到一个实现了 UserDao 接口的 bean，注入到 userDao
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StepMapper stepMapper;

    @Autowired
    private HttpUtils httpUtils;

    @Override
    public AccessResult loginWithCode(String code) {
//        return null;
        AccessResult result = getSessionWx(code);
        if(result.getErrcode()!=0){
            return result;
        }
        String openId = result.getValue().get("openid").toString();
        String sessionKey = result.getValue().get("session_key").toString();
        String sessionId = Md5(sessionKey);

        Map<String,String> map = new HashMap<>();
        map.put("sessionId",sessionId);

        ProcessUserInfo(openId,sessionKey,sessionId);
        return new AccessResult(TreeCode.SUCCESS_CODE,TreeCode.SUCCESS_MESSAGE,map);
    }

    @Override
    public User getUserByOpenId(String openId){
        return userMapper.selectByOpenId(openId);
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

    //    @Override
//    public AccessResult insertUser(User user) {
//        int result = userMapper.insertUser(user);
//        AccessResult accessResult = new AccessResult();
//        String str = " ";
//        if (result == 1) {
//            str = "成功";
//        } else {
//            str = "失败";
//        }
//        accessResult.setErrcode(result == 1 ? 0 : 1);
//        accessResult.setErrmsg(str);
//        return accessResult;
//    }

    @Override
    public AccessResult stepInfo(String info, int userId) {
        //解析json字符串
        List<JSONObject> list = JSONArray.parseArray(info, JSONObject.class);

        for (JSONObject object : list) {
            String date = (String) object.get("date");
            int step = (int) object.get("step");
//            System.out.println("date : " + date);
//            System.out.println( "step : " + step);

            if(isToday(date)){
                Step s = stepMapper.getTodayStep(userId);
                if(s == null){//表示step表没有当天数据
                    //insert
//                    stepMapper.insertStep(userId,step);
                    int i = stepMapper.insertStep(userId,step);
                    if(i != 0){
                        System.out.println("插入成功");
                    }else{
                        System.out.println("插入失败");
                    }
                }else{
                    //update
                    stepMapper.updateStep(userId,step);
                    int j = stepMapper.updateStep(userId,step);
                    if(j != 0){
                        System.out.println("更新成功");
                    }else{
                        System.out.println("更新失败");
                    }
                }
                return new AccessResult(0,"更新步数成功",null);
            }

        }
        return null;
    }

    public static boolean isToday(String date){
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowString = sf.format(now);
        if (nowString.equals(date)){
            return true;
        }
        return false;
    }



    @Override
    public List<User> userInfo(int age){
        return userMapper.userInfo(age);
    }

    @Override
    public void toStatistic(){
        userMapper.toStatistic();
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
}
