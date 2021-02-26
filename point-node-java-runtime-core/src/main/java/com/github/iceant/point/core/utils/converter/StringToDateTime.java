package com.github.iceant.point.core.utils.converter;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringToDateTime implements IConverter<String, Date>{
    Integer dateStyle;
    Integer timeStyle;
    Locale locale;
    Calendar calendar;
    Boolean lenient;
    TimeZone timeZone;
    NumberFormat numberFormat;
    String pattern;

    public DateFormat getDateFormat(){
        if(pattern!=null){
            return new SimpleDateFormat(pattern);
        }
        locale=(locale==null)? Locale.getDefault():locale;
        DateFormat dateFormat = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
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
        try {
            return getDateFormat().parse(s);
        } catch (Exception e) {
        }
        return def;
    }
}