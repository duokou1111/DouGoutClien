package com.client.rest.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Objects;

public class JsonUsernamePasswordAuthenFilter extends UsernamePasswordAuthenticationFilter {

        public JsonUsernamePasswordAuthenFilter() {
            super.setFilterProcessesUrl("/login");
        }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        BufferedReader reader = new BufferedReader( new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = reader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
      //  JSONObject jsonObj = JSON.parseObject(responseStrBuilder.toString());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("test","test");
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException, UnsupportedEncodingException {

        if(authentication.getPrincipal() instanceof MyUserDetails){
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            Collection collection = myUserDetails.getAuthorities();
            String token = "";
            if(Objects.nonNull(collection) && !collection.isEmpty()){
                GrantedAuthority authority = (GrantedAuthority) collection.iterator().next();
                token = JwtService.generateJwt(myUserDetails.getUsername(),authority.getAuthority());
            }else{
                token = JwtService.generateJwt(myUserDetails.getUsername());
            }
            response.setHeader("Authorization", token);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("??f");
    }
}
