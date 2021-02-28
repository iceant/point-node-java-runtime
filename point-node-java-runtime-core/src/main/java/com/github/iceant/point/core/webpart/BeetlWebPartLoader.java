package com.github.iceant.point.core.webpart;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.ResourceLoader;
import org.beetl.core.Template;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BeetlWebPartLoader implements IWebPartLoader{
    final ResourceLoader resourceLoader;
    final GroupTemplate groupTemplate;

    public BeetlWebPartLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        Configuration cfg = null;
        try {
            cfg = Configuration.defaultConfiguration();
            this.groupTemplate = new GroupTemplate(resourceLoader, cfg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getRelativePath(String path, String prefix){
        int pos = path.indexOf(prefix);
        if(pos==-1) return path;
        return path.substring(pos+prefix.length());
    }

    private boolean isJavascript(String resource){
        return resource.endsWith(".js");
    }

    public boolean isHtml(String resource){
        return resource.endsWith(".html") || resource.endsWith(".htm");
    }

    public boolean isStyle(String resource){
        return resource.endsWith(".css");
    }

    @Override
    public WebPart load(String name, Map args) {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        WebPart webPart = new WebPart().setName(name);
        Long webPartLastModified = 0L;
        Map<String, Object> sharedVars = groupTemplate.getSharedVars();
        try {
            if(args!=null) {
                groupTemplate.setSharedVars(args);
            }
            Resource[] resources = resourcePatternResolver.getResources("classpath:templates/webparts/"+name+"/**/**");
            List<WebPartItem> scripts = new ArrayList<>();
            List<WebPartItem> htmls = new ArrayList<>();
            List<WebPartItem> styles = new ArrayList<>();
            for(Resource resource : resources){
                String relativePath = getRelativePath(resource.getURI().toString(), "templates/webparts/");
                long lastModified = resource.getFile().lastModified();
                webPartLastModified = webPartLastModified<lastModified?lastModified:webPartLastModified;
                Template template = groupTemplate.getTemplate(relativePath);
                String content = template.render();
                if(content!=null && content.length()>0) {
                    WebPartItem webPartItem = new WebPartItem().setName(resource.getFilename())
                            .setPath(getRelativePath(resource.getURI().toString(), "templates/webparts/"+name+'/'))
                            .setContent(content)
                            .setLastModified(lastModified);
                    if (isJavascript(resource.getFilename())) {
                        scripts.add(webPartItem);
                    } else if (isHtml(resource.getFilename())) {
                        htmls.add(webPartItem);
                    } else if (isStyle(resource.getFilename())) {
                        styles.add(webPartItem);
                    }
                }
            }
            webPart.setScripts(scripts.size()>0?scripts:null);
            webPart.setHtmls(htmls.size()>0?htmls:null);
            webPart.setStyles(styles.size()>0?styles:null);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            groupTemplate.setSharedVars(sharedVars);
        }
        return webPart.setLastModified(webPartLastModified);
    }
}
