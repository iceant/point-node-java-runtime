package com.github.iceant.point.core.controller;

import com.github.iceant.point.core.beans.WebResponse;
import com.github.iceant.point.core.utils.AppUtil;
import com.github.iceant.point.core.webpart.IWebPartLoader;
import com.github.iceant.point.core.webpart.WebPart;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(path = {"/webpart"})
public class WebPartController {

    private final IWebPartLoader webPartLoader;

    public WebPartController(IWebPartLoader webPartLoader) {
        this.webPartLoader = webPartLoader;
    }

    @RequestMapping(path = {"/**", ""})
    public Object webpart(HttpServletRequest request, HttpServletResponse response){
        String uri = request.getRequestURI();
        String requestParts = uri.substring(uri.indexOf("/webpart/")+"/webpart/".length());
        String requestPartNames[] =  requestParts.split(",");
        List<WebPart> webPartList = new ArrayList<>();

        Map<String, Object> args = new HashMap<>();
        args.put("request", request);
        args.put("response", response);
        args.put("util", new AppUtil());
        for(String part : requestPartNames){
            WebPart webPart = webPartLoader.load(part, args);
            webPartList.add(webPart);
        }
        return WebResponse.success(HttpStatus.OK.value()).setData(webPartList);
    }
}
