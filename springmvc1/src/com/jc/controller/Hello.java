package com.jc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
//@RequestMapping("/jc")
public class Hello  {
    @RequestMapping(value = "/gethello" , method = RequestMethod.GET)
    public String handleRequest(int id , String name , Model model) throws Exception {
//        ModelAndView mv = new ModelAndView();
//        String id = httpServletRequest.getParameter("id");
//        String name = httpServletRequest.getParameter("name");
//        mv.addObject("school", id+name);
//        mv.addObject("message", "hello world!");
//        mv.setViewName("welcome");
//        return mv;
//        Model model = new BindingAwareModelMap();
//        String id = httpServletRequest.getParameter("id");
//        String name = httpServletRequest.getParameter("name");
        model.addAttribute("school", id+name);
        return  "welcome";


    }
    @RequestMapping("/study")
    public ModelAndView mystudy(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("school", "Study ");
        mv.addObject("message", "Study hard!");
        mv.setViewName("welcome");
        return mv;
    }


}
