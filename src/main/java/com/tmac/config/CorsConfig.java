package com.tmac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;

@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    @Value("${application.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigure() {
        String[] origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .collect(toList())
                .toArray(new String[0]);
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(origins)
                        .allowedMethods("GET", "POST", "DELETE", "PUT")
                        .exposedHeaders(HttpHeaders.AUTHORIZATION)
                        .allowedHeaders(
                                HttpHeaders.AUTHORIZATION,
                                HttpHeaders.CONTENT_LENGTH,
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.ACCEPT,
                                HttpHeaders.ACCEPT_ENCODING,
                                HttpHeaders.CONNECTION
                        );
            }
        };
    }
}
