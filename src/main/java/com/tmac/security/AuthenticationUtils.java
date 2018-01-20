package com.tmac.security;

import com.tmac.entity.Function;
import com.tmac.entity.User;
import com.tmac.vo.UserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.time.DateUtils.addHours;
import static org.apache.commons.lang3.time.DateUtils.addMinutes;

public final class AuthenticationUtils {
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String SECRET_KEY = "88888888";
    private static final int TOKEN_REFRESH_INTERVAL_MINUTES = 5;
    private static final int TOKEN_EXPIRED_HOURS = 2;
    private static final String CLAIM_NAME_FUNCTIONS = "FUNCTIONS";

    private AuthenticationUtils() {
    }

    public static Authentication attemptAuthentication(final HttpServletRequest request,
                                                       final HttpServletResponse response) {
        // to authentication
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> StringUtils.startsWith(header, TOKEN_PREFIX))
                .map(header -> StringUtils.removeStart(header, TOKEN_PREFIX))
                .map(AuthenticationUtils::parseJwtToken)
                .map(jwt -> refreshToken(response, jwt))
                .map(AuthenticationUtils::toAuthentication)
                .orElse(null);
    }

    private static Jws<Claims> parseJwtToken(final String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage(), e);
        }
    }

    private static Authentication toAuthentication(final Jws<Claims> jwt) {
        final Claims body = jwt.getBody();
        final List<Function> functions = Arrays.stream(body.get(CLAIM_NAME_FUNCTIONS, String.class).split(","))
                .filter(StringUtils::isNotEmpty)
                .map(Function::new)
                .collect(Collectors.toList());
        final UserVo user = new UserVo();
        user.setUsername(body.getSubject());
        if (StringUtils.isNoneEmpty(body.getId())) {
            user.setId(body.getId());
        }
        user.setFunctions(functions);
        final Authentication authentication = toAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private static Jws<Claims> refreshToken(final HttpServletResponse response, final Jws<Claims> jwt) {
        final Date issuedAt = jwt.getBody().getIssuedAt();
        final Date now = new Date();
        if (now.after(addMinutes(issuedAt, TOKEN_REFRESH_INTERVAL_MINUTES))) {
            final String newToken = Jwts.builder()
                    .setClaims(jwt.getBody())
                    .setIssuedAt(now)
                    .setExpiration(addHours(now, TOKEN_EXPIRED_HOURS))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
            setAuthHeader(response, newToken);
        }
        return jwt;
    }

    private static void setAuthHeader(final HttpServletResponse response, final String token) {
        response.setHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + token);
    }

    public static Authentication toAuthentication(final UserVo userVo) {
        return new UsernamePasswordAuthenticationToken(
                userVo,
                userVo.getPassword(),
                userVo.getFunctions()
                        .stream()
                        .map(Function::getId)
                        .map(functionId -> new SimpleGrantedAuthority(String.valueOf(functionId)))
                        .collect(Collectors.toSet())
        );
    }

    public static boolean addAuthorizationHeader(final HttpServletResponse response, final Authentication authentication) {
        final Date now = new Date();
        final UserVo user = (UserVo) authentication.getPrincipal();
        final Claims claims = new DefaultClaims()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(addHours(now, TOKEN_EXPIRED_HOURS));
        if (user.getId() != null) {
            claims.setId(user.getId());
        }
        claims.put(CLAIM_NAME_FUNCTIONS,
                user.getFunctions().stream().map(Function::getId).collect(Collectors.joining(","))
        );
        setAuthHeader(response, Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact()
        );
        return true;
    }
}
