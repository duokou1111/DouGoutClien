package com.client.rest.config;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailService  implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setUsername("test");
        GrantedAuthority grantedAuthority = ()->{return "1";};
        myUserDetails.setAuthorities(Collections.singleton(grantedAuthority));
        myUserDetails.setPassword(new BCryptPasswordEncoder().encode("test"));
       // myUserDetails.setPassword("test");
        return myUserDetails;
    }

}
