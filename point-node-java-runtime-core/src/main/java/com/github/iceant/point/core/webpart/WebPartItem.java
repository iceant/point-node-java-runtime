package com.github.iceant.point.core.webpart;

public class WebPartItem {
    private String name;
    private String path;
    private String content;
    private Long lastModified;
    ////////////////////////////////////////////////////////////////////////////////
    ////


    public Long getLastModified() {
        return lastModified;
    }

    public WebPartItem setLastModified(Long lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public String getName() {
        return name;
    }

    public WebPartItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public WebPartItem setPath(String path) {
        this.path = path;
        return this;
    }

    public String getContent() {
        return content;
    }

    public WebPartItem setContent(String content) {
        this.content = content;
        return this;
    }
}
