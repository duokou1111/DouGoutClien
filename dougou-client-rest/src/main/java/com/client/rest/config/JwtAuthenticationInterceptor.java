package com.client.rest.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @program: DouGoutClient
 * @description: Jwt拦截器
 * @author: zihan.wu
 * @create: 2020-12-21 00:08
 **/
public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            return false;
        }else{
            String[] auths = jwtService.getAuthority(token);
            Arrays.stream(auths).forEach(System.out::println);
        }
        return false;
    }
}
