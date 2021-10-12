package com.jc.treespringboot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
//@RequestMapping("/jc")
public class Hello  {
    @RequestMapping(value = "/gethello" , method = RequestMethod.GET)
    public String handleRequest() throws Exception {
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
//        model.addAttribute("school", id+name);
        return  "hello word!";


    }
    @RequestMapping("/study")
    public ModelAndView mystudy(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("school", "Study ");
        mv.addObject("message", "Study hard!");
        mv.setViewName("welcome");
        return mv;
    }


}
