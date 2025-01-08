package com.example.hnh.global.config.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * create on 2024. 12. 21. create by IntelliJ IDEA.
 *
 * @author Seokgyu Hwang (Chris)
 * @version 1.0
 * @see <a href="https://www.baeldung.com/spring-security-exceptionhandler">Handle Spring Security
 * Exceptions</a>
 * @since 1.0
 */
@Component
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Spring Security 예외를 처리하기 위한 resolver.
   */
  private final HandlerExceptionResolver resolver;

  /**
   * 생성자.
   */
  public DelegatedAuthenticationEntryPoint(
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.resolver = resolver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) {
    resolver.resolveException(request, response, null, authException);
  }
}