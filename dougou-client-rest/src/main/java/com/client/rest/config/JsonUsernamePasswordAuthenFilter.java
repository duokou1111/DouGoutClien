package com.client.rest.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonUsernamePasswordAuthenFilter extends AbstractAuthenticationProcessingFilter {
    protected JsonUsernamePasswordAuthenFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        BufferedReader reader = new BufferedReader( new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = reader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        JSONObject jsonObj = JSON.parseObject(responseStrBuilder.toString());
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                jsonObj.getString("username"),jsonObj.getString("password"));

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
