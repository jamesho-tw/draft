package com.example.attendance.configuration;

import com.example.attendance.data.model.entity.Token;
import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.service.TokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

  protected static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

  private static final String BEARER_TYPE = "Bearer";

  @Autowired
  private TokenService tokenService;

  @Autowired
  @Qualifier("userDetailsService")
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
    String bearer = String.format("%s ", BEARER_TYPE);
    if (authorization != null && authorization.startsWith(bearer)) {
      String accessToken = authorization.substring(BEARER_TYPE.length()).trim();
      logger.debug(String.format("Access token: %s", accessToken));

      Token token = tokenService.loadToken(accessToken);
      if (token != null) {
        logger.debug(String.format("Token: %s", token));
        if (isTokenExpired(token)) {
          UserDetails userDetails = userDetailsService
              .loadUserByUsername(getUsername(getUser(token)));
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities()
          );
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
          // revoke token
          tokenService.revokeToken(token);
          logger.debug("Revoke access token...");
        }
      } else {
        logger.debug("Token not found...");
      }
    }
    chain.doFilter(request, response);
  }

  private boolean isTokenExpired(Token token) {
    if (token == null) {
      return false;
    }
    long expired = (token.getExpirationTime() == null) ? 0 : token.getExpirationTime();
    return (expired > 0 && expired > System.currentTimeMillis()) ? true : false;
  }

  private User getUser(Token token) {
    if (token == null) {
      return null;
    }
    return token.getUser();
  }

  private String getUsername(User user) {
    if (user == null) {
      return null;
    }
    return user.getUsername();
  }

}
