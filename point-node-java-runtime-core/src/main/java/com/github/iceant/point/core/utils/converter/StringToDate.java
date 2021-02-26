package com.github.iceant.point.core.utils.converter;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringToDate implements IConverter<String, Date>{

    Integer dateStyle;
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
        DateFormat dateFormat = DateFormat.getDateInstance(dateStyle, locale);
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
            getDateFormat().parse(s);
        }catch (Exception err){ }
        return def;
    }

    //////////////////////////////////////////////////////////////////////
    ////

    public Integer getDateStyle() {
        return dateStyle;
    }

    public StringToDate setDateStyle(Integer dateStyle) {
        this.dateStyle = dateStyle;
        return this;
    }

    public Locale getLocale() {
        return locale;
    }

    public StringToDate setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public StringToDate setCalendar(Calendar calendar) {
        this.calendar = calendar;
        return this;
    }

    public Boolean getLenient() {
        return lenient;
    }

    public StringToDate setLenient(Boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public StringToDate setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public StringToDate setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
        return this;
    }
}