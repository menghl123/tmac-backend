package com.tmac.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmac.service.UserService;
import com.tmac.vo.LoginRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.tmac.security.AuthenticationUtils.addAuthorizationHeader;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private UserService userService;

    public JWTLoginFilter(final RequestMatcher requiresAuthenticationRequestMatcher, final UserService userService) {
        super(requiresAuthenticationRequestMatcher);
        setAllowSessionCreation(false);
        this.userService = userService;
        this.setAuthenticationSuccessHandler(((httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(HttpStatus.OK.value());
            OBJECT_MAPPER.writeValue(httpServletResponse.getOutputStream(),
                    authentication.getPrincipal());
        }));
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest httpServletRequest,
                                                final HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {
        final LoginRequest loginRequest = OBJECT_MAPPER
                .readValue(httpServletRequest.getInputStream(), LoginRequest.class);
        return Optional.ofNullable(userService.login(loginRequest.getAccount(), loginRequest.getPassword()))
                .map(AuthenticationUtils::toAuthentication)
                .filter(authentication -> addAuthorizationHeader(httpServletResponse, authentication))
                .orElseThrow(() -> new BadCredentialsException("用户名和密码错误"));
    }
}
