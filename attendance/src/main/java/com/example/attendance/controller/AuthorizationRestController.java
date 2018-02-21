package com.example.attendance.controller;

import com.example.attendance.data.model.entity.Token;
import com.example.attendance.data.model.entity.User;
import com.example.attendance.data.model.vo.AccessToken;
import com.example.attendance.data.service.TokenService;
import com.example.attendance.data.service.UserService;
import java.io.IOException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationRestController {

  protected static final Logger logger = LoggerFactory.getLogger(AuthorizationRestController.class);

  // zero or negative for non-expiring tokens
  private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1800; // 3 min

  private static final String BASIC_TYPE = "Basic";
  private static final String BEARER_TYPE = "Bearer";

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  // create access token
  @RequestMapping(value = "/token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createToken(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization)
      throws ServletRequestBindingException, IOException {
    if (!authorization.startsWith(String.format("%s ", BASIC_TYPE))) {
      throw new ServletRequestBindingException("Invalid basic authentication token");
    }

    String[] credentials = extractAndDecodeHeader(authorization);
    assert credentials.length == 2;
    String username = credentials[0];
    String password = credentials[1];

    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = userService.loadUserByUsername(authentication.getName());

    // create access token
    return getResponse(getAccessToken(tokenService.createToken(user)), HttpStatus.CREATED);
  }

  // TODO: revoking access token
  @RequestMapping(value = "/token/revoke", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> revokeToken(
      @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization)
      throws ServletRequestBindingException {
    if (!authorization.startsWith(String.format("%s ", BEARER_TYPE))) {
      throw new ServletRequestBindingException("Invalid access token");
    }
    String accessToken = authorization.substring(BEARER_TYPE.length()).trim();
    Token token = tokenService.loadToken(accessToken);
    tokenService.revokeToken(token);
    return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
  }

  private String[] extractAndDecodeHeader(String header) throws IOException {
    byte[] base64Token = header.substring(6).getBytes("UTF-8");
    byte[] decoded;
    try {
      decoded = Base64.getDecoder().decode(base64Token);
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException("Failed to decode basic authentication token");
    }

    String token = new String(decoded, "UTF-8");

    int delimiter = token.indexOf(":");
    if (delimiter == -1) {
      throw new BadCredentialsException("Invalid basic authentication token");
    }
    return new String[]{token.substring(0, delimiter), token.substring(delimiter + 1)};
  }

  private AccessToken getAccessToken(Token token) {
    if (token == null) {
      return null;
    }
    // TODO: scope
    return new AccessToken(token.getToken(), BEARER_TYPE, ACCESS_TOKEN_VALIDITY_SECONDS, null, "");
  }

  private ResponseEntity<AccessToken> getResponse(AccessToken accessToken, HttpStatus status) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Cache-Control", "no-store");
    headers.set("Pragma", "no-cache");
    return new ResponseEntity<AccessToken>(accessToken, headers, status);
  }

}
