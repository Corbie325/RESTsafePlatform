package com.example.restsafeplatform.common.configuration;

import com.example.restsafeplatform.authorization.dto.model.Role;
import com.example.restsafeplatform.authorization.service.JwtConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfiguration(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/api/1/login").permitAll()
                .antMatchers("/api/1/admin_registration").permitAll()
                .antMatchers("/api/1/registration").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HashMap<String, Object> getToken(){
        return new HashMap<>();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}