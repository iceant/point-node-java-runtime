package com.github.iceant.point.core.config;

import com.ibeetl.starter.BeetlTemplateCustomize;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.web.WebVariable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnClass(value = {BeetlGroupUtilConfiguration.class})
public class BeetlTemplateConfig {

    private final WebApplicationContext wac;

    public BeetlTemplateConfig(WebApplicationContext wac) {
        this.wac = wac;
    }

    @Bean(name = {"beetlTemplateCustomize"})
    public BeetlTemplateCustomize beetlTemplateCustomize() {
        return new BeetlTemplateCustomize() {
            public void customize(GroupTemplate groupTemplate) {
                Map<String, Object> sharedVars = new HashMap<String, Object>();
                groupTemplate.setSharedVars(sharedVars);
                groupTemplate.registerFunction("i18n", new Function() {
                    @Override
                    public Object call(Object[] obj, Context context) {
                        HttpServletRequest request = (HttpServletRequest) context.getGlobal(WebVariable.REQUEST);
                        RequestContext requestContext = new RequestContext(request);
                        String message = requestContext.getMessage((String) obj[0]);
                        return message;
                    }
                });
            }
        };
    }

}