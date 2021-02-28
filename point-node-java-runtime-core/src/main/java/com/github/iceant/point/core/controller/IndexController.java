package com.github.iceant.point.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @RequestMapping(path = {"","/index", "/home", "/"})
    public ModelAndView index(HttpServletRequest req){
        return new ModelAndView("pages/index.html");
    }

    @RequestMapping(path = {"/pages/login"})
    public ModelAndView login(){
        return new ModelAndView("pages/login.html");
    }

}