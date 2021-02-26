package com.github.iceant.point.core.utils.converter;

public class StringToLong implements IConverter<String, Long> {
    @Override
    public Long convertFromAToB(String s, Long def) {
        if(s==null) return def;
        try{
            return Long.valueOf(s);
        }catch (Exception err){}
        return def;
    }
}