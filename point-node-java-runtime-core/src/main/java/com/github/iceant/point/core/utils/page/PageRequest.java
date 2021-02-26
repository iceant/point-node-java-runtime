package com.github.iceant.point.core.utils.page;

public class PageRequest {
    private int pageNumber;
    private int pageSize;

    ////////////////////////////////////////////////////////////////////////////////
    ////

    public int getPageNumber() {
        return pageNumber;
    }

    public PageRequest setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageRequest setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}