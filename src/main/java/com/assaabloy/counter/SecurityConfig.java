//package com.assaabloy.counter;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .anyRequest()
//            .permitAll()
//            .and().csrf().disable();
//    }
//}
