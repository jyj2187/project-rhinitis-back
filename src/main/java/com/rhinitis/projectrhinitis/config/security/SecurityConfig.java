package com.rhinitis.projectrhinitis.config.security;

import com.rhinitis.projectrhinitis.util.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
//    private final AuthenticationProviderService authenticationProvider;
    private final PrincipalDetailsService principalDetailsService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
//                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(principalDetailsService)
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(new AntPathRequestMatcher("/members/v1/join"),
                                    new AntPathRequestMatcher("/members/v1/login")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/posts/v1/**", "GET")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/comments/v1/**", "GET")).permitAll()
                            .anyRequest().permitAll();
//                            .anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
