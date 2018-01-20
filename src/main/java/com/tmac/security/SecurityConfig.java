package com.tmac.security;

import com.tmac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final static String LOGIN_URL = "/api/login";
    private final UserService userService;
    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public SecurityConfig(final UserService userService, final RequestMappingHandlerMapping handlerMapping) {
        this.userService = userService;
        this.handlerMapping = handlerMapping;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .configurationSource(corsConfigurationSource(handlerMapping))
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, LOGIN_URL)
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/upload/image","/api/fileUpload")
                .permitAll()
                .antMatchers("/api/menus", "/api/menus/**",
                        "/api/functions", "/api/functions/**",
                        "/api/roles", "/api/roles/**",
                        "/api/users", "/api/users/**",
                        "/api/articles", "/api/articles/**")
                .authenticated()
                .and()
                .headers()
                .frameOptions().disable()
                .cacheControl()
                .and()
                .and()
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    private JWTLoginFilter loginFilter() {
        return new JWTLoginFilter(new AntPathRequestMatcher(LOGIN_URL, HttpMethod.POST.name()), userService);
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource(final RequestMappingHandlerMapping handlerMapping) {
        final UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.setCorsConfigurations(handlerMapping.getCorsConfigurations());
        return corsConfigurationSource;
    }
}
