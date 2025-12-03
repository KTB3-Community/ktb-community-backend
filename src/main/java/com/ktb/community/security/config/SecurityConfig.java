package com.ktb.community.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                // 폼 로그인 x
                .formLogin(AbstractHttpConfigurer::disable)
                // 브라우저 팝업 x
                .httpBasic(AbstractHttpConfigurer::disable)
                // Stateless 설정
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 경로 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/sessions", "/users", "/refresh").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                // 필터 추가
                // UsernamePasswordAuthenticationFilter보다 앞에 jwtFilter 추가
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // cors 설정
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    // cors 세부사항 설정
    // (공식 문서 기준)
    // corsFilter 따로 구현 필요 없음, Spring Security가 알아서 맨 앞단에 corsFilter 설정해줌
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("http://localhost:3000");
        config.addAllowedOriginPattern("http://localhost:8080");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("Set-Cookie");  // refresh token을 위한 쿠키 설정 추가

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }


}
