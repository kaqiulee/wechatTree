package com.jc.controller;


import com.jc.service.UserService;
import com.jc.support.AccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private User user;

    @RequestMapping("/stepInfo")
    @ResponseBody
    AccessResult stepInfo(String info, int userId){
//        1.设计step表，user_id,data,step
//        2.解析用户的Json运动数据
//        3.编码
        AccessResult result =userService.stepInfo(info,userId);
        return result;
    }

    @RequestMapping("/loginWithCode")
    @ResponseBody
    AccessResult loginWithCode(String code){
        AccessResult result = userService.loginWithCode(code);
        return result;
    }


    // 0. 在 dispatcher-servlet.xml 中增加配置，设置视图前缀和后缀为 /WEB-INF/content 和 .jsp

    // 1. 改为组合注解 @GetMapping 返回regist.jsp 页面
    @GetMapping(value = "/toregist")
    public String toRegist (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return  "regist";
    }

    //2.  改为组合注解 @PostMapping 将注册的用户名和密码保存在session里面
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String regist(User user,
                         HttpSession session,
                         Model model) throws Exception {
        //1将user用户名和密码打印出来
        //2返回前端result。jsp。显示注册成功
        model.addAttribute("sc","注册成功");
        model.addAttribute("school","user"+user+"session"+session);
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        return "result";
    }

    //3.返回 login.jsp
    @GetMapping("/tologin")
    public String toLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return null;
    }

    //4. 判断登录信息和 session中的是否一致，从而决定是否登陆成功
    @PostMapping(value = "/login")
    public String login(User user,
                        HttpSession session,
                        Model model) throws Exception {

        return null;
    }


}
