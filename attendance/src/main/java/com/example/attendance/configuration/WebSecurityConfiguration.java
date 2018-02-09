package com.example.attendance.configuration;

import com.example.attendance.data.service.impl.UserDetailsServiceImpl;
import javax.servlet.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  protected static final Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);

  @Autowired
  @Qualifier("userDetailsService")
  private UserDetailsService userDetailsService;

  @Override
  protected void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().exceptionHandling()
        .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers("/**").permitAll()
        .anyRequest().authenticated();
    http.addFilterAfter(new AuthenticationTokenFilter(), BasicAuthenticationFilter.class);
    http.headers().cacheControl();
  }

//  @Bean
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }

}
