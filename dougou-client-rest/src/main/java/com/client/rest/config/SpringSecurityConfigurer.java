package com.client.rest.config;

import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Component
@EnableWebSecurity
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {
    JsonUsernamePasswordAuthenFilter authenFilter;
    public SpringSecurityConfigurer(){
        this.authenFilter = new JsonUsernamePasswordAuthenFilter();
    }
    @Autowired @Lazy
    JsonUsernamePasswordAuthenFilter jsonUsernamePasswordAuthenFilter;
    @Autowired @Lazy
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailService userDetailService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(CorsConfigurationSource());
        http.authorizeRequests().anyRequest().permitAll().and().formLogin().and().csrf().disable();
        http.addFilterAt(jsonUsernamePasswordAuthenFilter, UsernamePasswordAuthenticationFilter.class);
    }
    private CorsConfigurationSource CorsConfigurationSource() {
        CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost");	//同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedOrigin("http://127.0.0.1");
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        //允许的请求方法，PSOT、GET等
        corsConfiguration.addExposedHeader("token");
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }
    @Bean
    JsonUsernamePasswordAuthenFilter jsonUsernamePasswordAuthenFilter() throws Exception {
       JsonUsernamePasswordAuthenFilter jsonUsernamePasswordAuthenFilter = new JsonUsernamePasswordAuthenFilter();
       jsonUsernamePasswordAuthenFilter.setAuthenticationManager(this.authenticationManager);
        return  jsonUsernamePasswordAuthenFilter;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authProvider;
    }
    //https://www.cnblogs.com/zhengqing/p/11704229.html
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //默认拦截所有路径
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }
    @Bean
    public JwtAuthenticationInterceptor authenticationInterceptor() {
        return new JwtAuthenticationInterceptor();
    }

}
