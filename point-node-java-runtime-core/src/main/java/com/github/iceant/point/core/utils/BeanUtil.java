package com.github.iceant.point.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanUtil {

    public static Field findFieldInType(Class type, String name){
        if(type==null || name==null) return null;
        Field field = null;
        try {
            field = type.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
        }

        try {
            field = type.getField(name);
            field.setAccessible(true);
            return field;
        }catch (Exception err){}


        Class parentType = type.getSuperclass();
        if(parentType!=null){
            field = findFieldInType(parentType, name);
            if(field!=null){
                field.setAccessible(true);
                return field;
            }
        }

        Class[] interfaces = type.getInterfaces();
        if(interfaces!=null){
            for(Class itf : interfaces){
                field = findFieldInType(itf, name);
                if(field!=null){
                    field.setAccessible(true);
                    return field;
                }
            }
        }

        return null;
    }

    public static Method findMethodInType(Class type, String name, Class... params){
        if(type==null || name==null) return null;
        Method method = null;

        try {
            method = type.getDeclaredMethod(name, params);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
        }
        try {
            method = type.getMethod(name, params);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
        }

        Class parentType = type.getSuperclass();
        method = findMethodInType(parentType, name, params);
        if(method!=null){
            method.setAccessible(true);
            return method;
        }

        Class[] interfaces = type.getInterfaces();
        if(interfaces!=null){
            for(Class itf:interfaces){
                method = findMethodInType(itf, name, params);
                if(method!=null){
                    method.setAccessible(true);
                    return method;
                }
            }
        }

        for(Method m : type.getMethods()){
            if(m.getName().equals(name)){
                return m;
            }
        }

        return null;
    }

    public static Field findField(Object obj, String name){
        if(obj==null || name==null) return null;
        return findFieldInType(obj.getClass(), name);
    }

    public static Method findMethod(Object obj, String name, Class ... params){
        if(obj==null || name==null) return null;
        return findMethodInType(obj.getClass(), name, params);
    }

    public static <T> T invoke(Object obj, String methodName, Class[] params, Object ... args){
        Method method = findMethod(obj, methodName, params);
        if(method==null){
            throw new RuntimeException(new NoSuchMethodException(methodName));
        }

        try {
            return (T) method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T invoke(Object obj, String methodName, Object ... args){
        Class[] params = null;
        if(args!=null){
            params = new Class[args.length];
            for(int i=0; i<args.length; i++){
                params[i] = args.getClass();
            }
        }
        Method method = findMethod(obj, methodName, params);
        if(method==null){
            throw new RuntimeException(new NoSuchMethodException(methodName));
        }

        try {
            return (T) method.invoke(obj, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(Object obj, String fieldName, Object value){
        Field field = findField(obj, fieldName);
        if(field!=null){
            try {
                field.set(obj, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static <T> T get(Object obj, String fieldName, T def){
        Field field = findField(obj, fieldName);
        if(field==null) return def;
        try {
            field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static String makeGetMethod(String fieldName){
        if(fieldName==null || fieldName.length()<1) return fieldName;
        return "get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
    }
    public static String makeSetMethod(String fieldName){
        if(fieldName==null || fieldName.length()<1) return fieldName;
        return "set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
    }

    public static <A,B> B copyAToB(A a, B b){
        if(a==null || b==null) return b;
        List<Method> getMethods = new ArrayList<>();

        for(Method method : a.getClass().getMethods()){
            String methodName = method.getName();
            if(method.getParameterCount()==0 && methodName.startsWith("get") && methodName.length()>3){
                Object value = null;
                Class returnType = method.getReturnType();
                try{
                    value =invoke(a, methodName);
                }catch (Exception err){}
                String setMethodName = "set"+methodName.substring(3);
                try {
                    invoke(b, setMethodName, new Class[]{returnType}, value);
                }catch (Exception err){}
            }
        }

        return b;
    }
}