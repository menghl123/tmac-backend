package com.tmac.security;

import com.google.common.collect.ImmutableList;
import com.tmac.AbstractTest;
import com.tmac.entity.Function;
import com.tmac.vo.UserVo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationUtilsTest extends AbstractTest {
    private static final String SECRET_KEY = "88888888";
    private static final int TOKEN_EXPIRED_HOURS = 2;
    private static final String CLAIM_NAME_FUNCTIONS = "FUNCTIONS";

    @Test
    public void should_authentication() {
        // when
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final String username = "admin";
        final Map<String, Object> map = new HashMap<>();
        map.put(CLAIM_NAME_FUNCTIONS, "");
        final Date now = DateUtils.addMinutes(new Date(), -10);
        when(request.getHeader(anyString())).thenReturn("Bearer" + Jwts.builder()
                .setClaims(map)
                .setIssuedAt(now)
                .setSubject(username)
                .setId("1")
                .setExpiration(DateUtils.addHours(now, TOKEN_EXPIRED_HOURS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact());
        // given
        final Authentication authentication = AuthenticationUtils.attemptAuthentication(request, response);

        // then
        assertThat(authentication.getPrincipal()).isInstanceOf(UserVo.class);
        then(response).should().setHeader(anyString(), anyString());
    }

    @Test(expected = AuthenticationException.class)
    public void should_not_authentication() {
        // when
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final String username = "admin";
        final Map<String, Object> map = new HashMap<>();
        map.put(CLAIM_NAME_FUNCTIONS, "");
        final Date now = DateUtils.addHours(new Date(), -1 - TOKEN_EXPIRED_HOURS);
        when(request.getHeader(anyString())).thenReturn("Bearer" + Jwts.builder()
                .setClaims(map)
                .setIssuedAt(now)
                .setSubject(username)
                .setId("1")
                .setExpiration(DateUtils.addHours(now, TOKEN_EXPIRED_HOURS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact());
        // given
        final Authentication authentication = AuthenticationUtils.attemptAuthentication(request, response);

        // then
    }

    @Test
    public void should_add_authentication_header() {
        // when
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Authentication authentication = mock(Authentication.class);
        final UserVo userVo = new UserVo();
        userVo.setId("test");
        final Function function = new Function();
        function.setId("test");
        userVo.setFunctions(ImmutableList.of(function));
        when(authentication.getPrincipal()).thenReturn(userVo);

        // given
        final boolean isSuccess = AuthenticationUtils.addAuthorizationHeader(response, authentication);

        // then
        assertThat(isSuccess).isTrue();
        then(response).should().setHeader(anyString(), anyString());
    }

    @Test
    public void should_to_authentication() {
        // when
        final UserVo userVo = new UserVo();
        userVo.setPassword("test");
        final Function function = new Function();
        function.setId("test");
        userVo.setFunctions(ImmutableList.of(function));

        // given
        final Authentication authentication = AuthenticationUtils.toAuthentication(userVo);

        // then
        assertThat(authentication.getPrincipal()).isEqualTo(userVo);
        assertThat(authentication.getAuthorities().size()).isEqualTo(userVo.getFunctions().size());
    }
}