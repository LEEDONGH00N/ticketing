package study.kiwi.ticketing.global.filter;//package com.example.security.common.filter;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.List;
//
//@Component
//public class DefaultCorsFilter extends CorsFilter {
//    public DefaultCorsFilter() {
//        super(corsConfigurationSource());
//    }
//    private static CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:8080");
//        configuration.setAllowedMethods(List.of("*"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
//}
