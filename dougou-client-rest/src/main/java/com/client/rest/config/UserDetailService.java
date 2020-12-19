package com.client.rest.config;
import com.client.core.Bo.BaseResponse;
import com.client.core.Bo.GetUserInfoBo;
import com.client.core.feign.UserServerRestTemplate;
import com.client.core.ibo.UsernameIbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class UserDetailService  implements UserDetailsService {
    @Autowired
    UserServerRestTemplate userServerRestTemplate;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BaseResponse<GetUserInfoBo> res= userServerRestTemplate.retriveUser(new UsernameIbo(username));
        MyUserDetails myUserDetails = new MyUserDetails();
        if (res.getSuccess()){
            myUserDetails.setUsername(username);
            myUserDetails.setPassword(res.getData().getPassword());
            Collection<GrantedAuthority> auths = res.getData().getRoleIds().stream().map(x->{
                GrantedAuthority auth = ()-> String.valueOf(x);
                return auth;
            }).collect(Collectors.toList());
            myUserDetails.setAuthorities(auths);
        }else{
            myUserDetails = null;
        }

        return myUserDetails;
    }

}
