package com.rhinitis.projectrhinitis.config.security;

import com.rhinitis.projectrhinitis.config.jwt.JwtProvider;
import com.rhinitis.projectrhinitis.config.jwt.filter.JwtAuthenticationFilter;
import com.rhinitis.projectrhinitis.config.jwt.filter.JwtAuthorizationFilter;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

        builder
                .addFilter(corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProvider))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository, jwtProvider));
    }
    
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("AUTH_RHINITIS");
        // 주소 체계 재정의 필요
        source.registerCorsConfiguration("**/v1/**", config);
        return new CorsFilter(source);
    }
}
