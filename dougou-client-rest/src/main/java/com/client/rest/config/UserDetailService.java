package com.client.rest.config;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDetailService  implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("?");
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setUsername("test");
        myUserDetails.setPassword(new BCryptPasswordEncoder().encode("test"));
       // myUserDetails.setPassword("test");
        return myUserDetails;
    }

}
