package com.github.iceant.point.core.beans;

public class WebResponse<T> {
    private T data;
    private int code;
    private String message;

    ////////////////////////////////////////////////////////////////////////////////
    ////
    public static <T> WebResponse<T> get(){
        return new WebResponse<>();
    }

    public static <T> WebResponse<T> success() {
        return new WebResponse<T>().setCode(1).setMessage("success");
    }

    public static <T> WebResponse<T> fail() {
        return new WebResponse<T>().setCode(-1).setMessage("fail");
    }

    public static <T> WebResponse<T> success(int code, String msg) {
        return new WebResponse<T>().setCode(code).setMessage(msg);
    }

    public static <T> WebResponse<T> fail(int code, String msg) {
        return new WebResponse<T>().setCode(code).setMessage(msg);
    }
    ////////////////////////////////////////////////////////////////////////////////
    ////


    public T getData() {
        return data;
    }

    public WebResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public WebResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public WebResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }
}