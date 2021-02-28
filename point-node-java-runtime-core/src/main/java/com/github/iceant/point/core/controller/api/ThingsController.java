package com.github.iceant.point.core.controller.api;

import com.github.iceant.point.core.beans.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = {"/api/things"})
public class ThingsController {


    @RequestMapping("/list")
    public Object list(){
        List<Map<String, String>> datas = new ArrayList<>();
        for(int i=0; i<10; i++){
            Map<String, String> data = new HashMap<>();
            data.put("id", String.valueOf(i));
            data.put("name", "User-"+i);
            data.put("age", String.valueOf(i+11));
            datas.add(data);
        }
        return WebResponse.success(HttpStatus.OK.value()).setData(datas);
    }
}
