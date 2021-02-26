package com.github.iceant.point.core.utils;

import com.github.iceant.point.core.utils.converter.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Locale;

@Component
public class AppUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    //////////////////////////////////////////////////////////////////////
    ////
    public static <T> T bean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public static <T> T bean(String name) {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T bean(Class<T> type, Object ... args){
        return applicationContext.getBean(type, args);
    }

    public static <T> T bean(String name, Object ... args) {
        return (T) applicationContext.getBean(name, args);
    }
    //////////////////////////////////////////////////////////////////////
    ////
    public static String propStr(String name, String def) {
        return applicationContext.getEnvironment().getProperty(name, def);
    }

    public static String propStr(String name) {
        return applicationContext.getEnvironment().getProperty(name);
    }

    public static Integer propInt(String name, Integer def) {
        String value = propStr(name, null);
        return new StringToInteger().convertFromAToB(name, def);
    }

    public static Integer propInt(String name) {
        String value = propStr(name, null);
        return new StringToInteger().convertFromAToB(name, null);
    }

    public static Long propLong(String name, Long def) {
        String value = propStr(name, null);
        return new StringToLong().convertFromAToB(name, def);
    }

    public static Long propLong(String name) {
        String value = propStr(name, null);
        return new StringToLong().convertFromAToB(name, null);
    }

    public static Short propShort(String name, Short def) {
        String value = propStr(name, null);
        return new StringToShort().convertFromAToB(name, def);
    }

    public static Short propShort(String name) {
        String value = propStr(name, null);
        return new StringToShort().convertFromAToB(name, null);
    }

    public static Float propFloat(String name, Float def) {
        String value = propStr(name, null);
        return new StringToFloat().convertFromAToB(name, def);
    }

    public static Float propFloat(String name) {
        String value = propStr(name, null);
        return new StringToFloat().convertFromAToB(name, null);
    }

    public static Double propDouble(String name, Double def) {
        String value = propStr(name, null);
        return new StringToDouble().convertFromAToB(name, def);
    }

    public static Double propDouble(String name) {
        String value = propStr(name, null);
        return new StringToDouble().convertFromAToB(name, null);
    }

    public static Number propNumber(String name, Number def) {
        String value = propStr(name, null);
        return new StringToNumber().convertFromAToB(value, def);
    }

    public static Number propNumber(String name, Number def, IConverter<String, Number> converter) {
        String value = propStr(name, null);
        return converter.convertFromAToB(value, def);
    }

    public static Date propDate(String name, Date def) {
        String value = propStr(name, null);
        return new StringToDate().convertFromAToB(value, def);
    }

    public static Date propTime(String name, Date def) {
        String value = propStr(name, null);
        return new StringToTime().convertFromAToB(value, def);
    }

    public static Date propDateTime(String name, Date def) {
        String value = propStr(name, null);
        return new StringToDateTime().convertFromAToB(value, def);
    }

    public static Date propDateTime(String name, Date def, IConverter<String, Date> converter) {
        String value = propStr(name, null);
        return converter.convertFromAToB(value, def);
    }

    public static Date propDate(String name, Date def, IConverter<String, Date> converter) {
        String value = propStr(name, null);
        return converter.convertFromAToB(value, def);
    }

    public static Date propTime(String name, Date def, IConverter<String, Date> converter) {
        String value = propStr(name, null);
        return converter.convertFromAToB(value, def);
    }

    //////////////////////////////////////////////////////////////////////
    ////
    public static HttpServletRequest servletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse servletResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }

    public static ServletContext servletContext() {
        return servletRequest().getServletContext();
    }

    public static HttpSession httpSession() {
        return servletRequest().getSession();
    }

    public static HttpSession httpSession(boolean create) {
        return servletRequest().getSession(create);
    }

    public static Cookie[] httpCookies(){
        return servletRequest().getCookies();
    }

    public static Locale getLocale(){
        return LocaleContextHolder.getLocale();
    }

    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static SecurityContext getSecurityContext(){
        return SecurityContextHolder.getContext();
    }
    //////////////////////////////////////////////////////////////////////
    ////
    public static String paramStr(String name, String def) {
        String value = servletRequest().getParameter(name);
        if (value == null) return def;
        return value;
    }

    public static String paramStr(String name) {
        return paramStr(name, null);
    }

    public static Integer paramInt(String name, Integer def) {
        String value = paramStr(name);
        return new StringToInteger().convertFromAToB(value, def);
    }

    public static Short paramShort(String name, Short def) {
        String value = paramStr(name);
        return new StringToShort().convertFromAToB(value, def);
    }

    public static Long paramLong(String name, Long def) {
        String value = paramStr(name);
        return new StringToLong().convertFromAToB(value, def);
    }

    public static Float paramFloat(String name, Float def) {
        String value = paramStr(name);
        return new StringToFloat().convertFromAToB(value, def);
    }

    public static Double paramDouble(String name, Double def) {
        String value = paramStr(name);
        return new StringToDouble().convertFromAToB(value, def);
    }

    public static Boolean paramBool(String name, Boolean def) {
        String value = paramStr(name);
        return new StringToBoolean().convertFromAToB(value, def);
    }

    public static Date paramDate(String name, Date def) {
        String value = paramStr(name);
        return new StringToDate().convertFromAToB(value, def);
    }

    public static Date paramTime(String name, Date def) {
        String value = paramStr(name);
        return new StringToTime().convertFromAToB(value, def);
    }

    public static Date paramDateTime(String name, Date def) {
        String value = paramStr(name);
        return new StringToDateTime().convertFromAToB(value, def);
    }

    public static Date paramDate(String name, Date def, IConverter<String, Date> converter) {
        String value = paramStr(name);
        return converter.convertFromAToB(value, def);
    }

    public static Number paramNumber(String name, Number def) {
        String value = paramStr(name);
        return new StringToNumber().convertFromAToB(value, def);
    }

    public static Number paramNumber(String name, Number def, IConverter<String, Number> converter) {
        String value = paramStr(name);
        return converter.convertFromAToB(value, def);
    }

    ////////////////////////////////////////////////////////////////////////////////
    //// msg
    public static String msg(Locale locale, String name, Object ... args){
        return applicationContext.getMessage(name, args, locale);
    }

    public static String msg(Locale locale, String name, String defaultMessage, Object ... args){
        return applicationContext.getMessage(name, args, defaultMessage, locale);
    }

    public static String msg(String code, Object ... args){
        return applicationContext.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String msg(String code, String defaultMessage, Object ... args){
        return applicationContext.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }
}