package com.github.iceant.point.core.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/webpart"})
public class WebPartController {
    @RequestMapping(path = {"/", ""})
    public Object webpart(String parts){
        return parts;
    }
}
