package com.github.iceant.point.core.utils.page;

public class Page<T> {
    T data;
    Integer pageNumber;
    Integer pageSize;
    Long dataSize;
    Long pageCount;

    ////////////////////////////////////////////////////////////////////////////////
    ////
    public static <T> Page<T> makePage(Integer pageNumber, Integer pageSize, T data, Long dataSize){
        return new Page<T>().setPageNumber(pageNumber).setPageSize(pageSize).setDataSize(dataSize).setData(data).build();
    }

    public static <T> Page<T> makeEmpty(Integer pageNumber, Integer pageSize){
        return new Page<T>().setPageSize(pageSize).setPageNumber(pageNumber).setDataSize(0L).setData(null).setPageCount(1L);
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////

    public Page<T> build(){
        pageCount = (dataSize + pageSize-1)/pageSize;
        return this;
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////

    public T getData() {
        return data;
    }

    public Page<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Page<T> setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Page<T> setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public Page<T> setDataSize(Long dataSize) {
        this.dataSize = dataSize;
        return this;
    }


    public Long getPageCount() {
        return pageCount;
    }

    public Page<T> setPageCount(Long pageCount) {
        this.pageCount = pageCount;
        return this;
    }
}