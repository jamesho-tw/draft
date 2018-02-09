package com.example.attendance.configuration;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

  protected static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // TODO:
    logger.debug("XXXXX");
    filterChain.doFilter(request, response);
  }

}
