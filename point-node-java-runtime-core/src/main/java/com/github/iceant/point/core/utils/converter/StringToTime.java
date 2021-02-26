package com.github.iceant.point.core.utils.converter;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringToTime implements IConverter<String, Date>{
    Integer timeStyle;
    Locale locale;
    Calendar calendar;
    Boolean lenient;
    TimeZone timeZone;
    NumberFormat numberFormat;
    String pattern;

    //////////////////////////////////////////////////////////////////////
    ////
    public DateFormat getDateFormat(){
        if(pattern!=null){
            return new SimpleDateFormat(pattern);
        }
        locale=(locale==null)?Locale.getDefault():locale;
        DateFormat dateFormat = DateFormat.getTimeInstance(timeStyle, locale);
        if(calendar!=null) {
            dateFormat.setCalendar(calendar);
        }
        if(lenient!=null) {
            dateFormat.setLenient(lenient);
        }
        if(timeZone!=null) {
            dateFormat.setTimeZone(timeZone);
        }
        if(numberFormat!=null) {
            dateFormat.setNumberFormat(numberFormat);
        }

        return dateFormat;
    }

    //////////////////////////////////////////////////////////////////////
    ////
    @Override
    public Date convertFromAToB(String s, Date def) {
        if(s==null) return def;
        try{
            return getDateFormat().parse(s);
        }catch (Exception err){}
        return def;
    }

    //////////////////////////////////////////////////////////////////////
    ////
    public Integer getTimeStyle() {
        return timeStyle;
    }

    public StringToTime setTimeStyle(Integer timeStyle) {
        this.timeStyle = timeStyle;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public StringToTime setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public StringToTime setCalendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }

    public Boolean getLenient() {
        return lenient;
    }

    public StringToTime setLenient(Boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public StringToTime setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public StringToTime setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
        return this;
    }

    public String getPattern() {
        return pattern;
    }

    public StringToTime setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }
}