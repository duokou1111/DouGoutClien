package com.client.rest.config;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
@Data
@NoArgsConstructor
public class MyUserDetails implements UserDetails {
    private Collection<GrantedAuthority> authorities;
    private String password;
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //不验证
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //不验证
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //不验证
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //不验证
    @Override
    public boolean isEnabled() {
        return true;
    }
}
