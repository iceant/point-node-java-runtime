package com.github.iceant.point.core.utils.converter;

public class StringToDouble implements IConverter<String, Double>{
    @Override
    public Double convertFromAToB(String s, Double def) {
        if(s==null) return def;
        try{
            return Double.valueOf(s);
        }catch (Exception err){}
        return def;
    }
}