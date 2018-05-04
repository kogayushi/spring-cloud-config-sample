package com.example.cloud_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableConfigServer
@SpringBootApplication
public class CloudServerApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(CloudServerApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
            .antMatchers("/**/logback*.xml").permitAll()
            .anyRequest().authenticated().and()
            .httpBasic().and()
            .csrf().disable();
    }
}
