package com.github.iceant.point.core.config;

import com.github.iceant.point.core.webpart.BeetlWebPartLoader;
import com.github.iceant.point.core.webpart.IWebPartLoader;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebPartConfig {
    @Bean
    IWebPartLoader webPartLoader(){
        return new BeetlWebPartLoader(new ClasspathResourceLoader("templates/webparts"));
    }
}
