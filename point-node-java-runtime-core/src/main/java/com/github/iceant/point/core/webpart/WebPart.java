package com.github.iceant.point.core.webpart;

import java.util.List;

public class WebPart {
    private String name;
    private Long lastModified;
    private List<WebPartItem> scripts;
    private List<WebPartItem> htmls;
    private List<WebPartItem> styles;

    ////////////////////////////////////////////////////////////////////////////////
    ////


    public String getName() {
        return name;
    }

    public WebPart setName(String name) {
        this.name = name;
        return this;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public WebPart setLastModified(Long lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public List<WebPartItem> getScripts() {
        return scripts;
    }

    public WebPart setScripts(List<WebPartItem> scripts) {
        this.scripts = scripts;
        return this;
    }

    public List<WebPartItem> getHtmls() {
        return htmls;
    }

    public WebPart setHtmls(List<WebPartItem> htmls) {
        this.htmls = htmls;
        return this;
    }

    public List<WebPartItem> getStyles() {
        return styles;
    }

    public WebPart setStyles(List<WebPartItem> styles) {
        this.styles = styles;
        return this;
    }
}
