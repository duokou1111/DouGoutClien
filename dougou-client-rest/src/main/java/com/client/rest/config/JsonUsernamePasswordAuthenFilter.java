package com.client.rest.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.client.rest.dto.BaseResponseDto;
import com.client.rest.enums.ResponseCodeEnum;
import com.client.rest.vo.LoginVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.*;
import java.util.stream.Collectors;

public class JsonUsernamePasswordAuthenFilter extends UsernamePasswordAuthenticationFilter {

        public JsonUsernamePasswordAuthenFilter() {
            super.setFilterProcessesUrl("/login");
        }
    @Autowired
    JwtService jwtService;
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        BufferedReader reader = new BufferedReader( new InputStreamReader(httpServletRequest.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = reader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        LoginVo loginVo = JSON.parseObject(responseStrBuilder.toString(),LoginVo.class);
        //UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginVo.getUsername(),new BCryptPasswordEncoder().encode(loginVo.getPassword()));
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginVo.getUsername(),loginVo.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException, UnsupportedEncodingException {

        if(authentication.getPrincipal() instanceof MyUserDetails){
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            Collection collection = myUserDetails.getAuthorities();
            String token = "";
            BaseResponseDto<Boolean> baseResponseDto = new BaseResponseDto<>(true);
            List<GrantedAuthority> list = new ArrayList<>();
            if(Objects.nonNull(collection) && !collection.isEmpty()){
                Iterator<GrantedAuthority> iterator = collection.iterator();
                while(iterator.hasNext()){
                    list.add(iterator.next());
                }
                token = jwtService.generateJwt(myUserDetails.getUsername(),list.stream().map(x->x.getAuthority()).toArray());
            }else{
                token = jwtService.generateJwt(myUserDetails.getUsername());
            }
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(baseResponseDto));
            response.setHeader("Authorization", token);
        }

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(BaseResponseDto.error(ResponseCodeEnum.RESPONSE_CODE_BUSSINESS_FAILURE.getCode(),"用户名或密码错误")));
    }
}
