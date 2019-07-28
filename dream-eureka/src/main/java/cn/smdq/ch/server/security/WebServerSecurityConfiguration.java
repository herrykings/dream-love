package cn.smdq.ch.server.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author shuimodanqing
 * create at:  2019/6/18  11:36 PM
 * @description
 */
@EnableWebSecurity
@Configuration
public class WebServerSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().and()
                .httpBasic();
    }
}
