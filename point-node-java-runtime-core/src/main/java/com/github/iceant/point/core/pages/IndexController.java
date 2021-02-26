package com.github.iceant.point.core.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping(path = {"","/index", "/home", "/"})
    public ModelAndView index(){
        return new ModelAndView("pages/index.html");
    }

    @RequestMapping(path = {"/pages/login"})
    public ModelAndView login(){
        return new ModelAndView("pages/login.html");
    }
}