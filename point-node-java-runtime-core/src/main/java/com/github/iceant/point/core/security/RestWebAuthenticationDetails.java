package com.github.iceant.point.core.security;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class RestWebAuthenticationDetails extends WebAuthenticationDetails {

    JsonNode params;

    public RestWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        params = readAsNode(request, 1024, "UTF-8");
    }

    ////////////////////////////////////////////////////////////////////////////////
    ////

    JsonNode readAsNode(HttpServletRequest request, int bufferSize, String charset){
        if (request == null) return null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        byte[] buffer = new byte[bufferSize];
        int count = 0;
        try {
            is= request.getInputStream();
            baos = new ByteArrayOutputStream();
            while ((count = is.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(baos.toString(charset));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                baos=null;
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String readAsString(InputStream is, int bufferSize, String charset) {
        if (is == null) return null;
        byte[] buffer = new byte[bufferSize];
        int count = 0;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            while ((count = is.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
            return baos.toString(charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            if(baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                baos=null;
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getUsername() {
        return params.get("username").asText();
    }

    public String getPassword() {
        return params.get("password").asText();
    }

}
