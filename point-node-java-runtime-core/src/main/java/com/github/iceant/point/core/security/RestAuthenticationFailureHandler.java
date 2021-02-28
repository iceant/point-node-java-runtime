package com.github.iceant.point.core.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iceant.point.core.beans.WebResponse;
import com.github.iceant.point.core.utils.AppUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private String getMessage(Exception e){
        if(e instanceof LockedException){
            return AppUtil.msg("security.authentication.error.locked");
        }else if(e instanceof BadCredentialsException){
            return AppUtil.msg("security.authentication.error.bad_credentials");
        }else if(e instanceof DisabledException){
            return AppUtil.msg("security.authentication.error.disabled");
        }else if(e instanceof AccountExpiredException){
            return AppUtil.msg("security.authentication.error.account_expired");
        }else if(e instanceof CredentialsExpiredException){
            return AppUtil.msg("security.authentication.error.credentials_expired");
        }else{
            return e.getMessage();
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader(HttpHeaders.CONTENT_TYPE))
                || MediaType.APPLICATION_JSON_VALUE.equals(request.getHeader(HttpHeaders.ACCEPT)) ){
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String ret = objectMapper.writeValueAsString(WebResponse.fail().setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setMessage(getMessage(exception)));
            PrintWriter out = response.getWriter();
            out.write(ret);
            out.flush();
            out.close();
        }else{
            response.sendRedirect("/");
        }
    }
}
