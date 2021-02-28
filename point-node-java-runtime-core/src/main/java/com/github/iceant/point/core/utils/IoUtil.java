package com.github.iceant.point.core.utils;

import java.io.*;

public class IoUtil {
    public static String readAsString(File file, int bufferSize, String charset){
        if(file==null) return null;
        bufferSize = bufferSize>0?bufferSize:1024;
        charset= charset==null?"UTF-8":charset;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = new byte[bufferSize];
        int count = 0;
        try {
            is = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            while ((count = is.read(bytes)) != -1) {
                baos.write(bytes, 0, count);
            }
            return baos.toString(charset);
        }catch (Exception err){
            throw new RuntimeException(err);
        }finally {
            if(baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                baos = null;
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                is = null;
            }
            bytes = null;
        }
    }

    public static String readAsStringAndCloseStream(InputStream inputStream, int bufferSize, String charset){
        if(inputStream==null) return null;
        bufferSize = bufferSize>0?bufferSize:1024;
        charset= charset==null?"UTF-8":charset;

        ByteArrayOutputStream baos = null;
        byte[] bytes = new byte[bufferSize];
        int count = 0;

        try {
            baos = new ByteArrayOutputStream();
            while ((count = inputStream.read(bytes)) != -1) {
                baos.write(bytes, 0, count);
            }
            return baos.toString(charset);
        }catch (Exception err){
            throw new RuntimeException(err);
        }finally {
            if(baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                baos = null;
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            bytes = null;
        }
    }
}
