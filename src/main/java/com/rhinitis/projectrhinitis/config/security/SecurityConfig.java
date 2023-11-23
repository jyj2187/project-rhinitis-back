package com.rhinitis.projectrhinitis.config.security;

import com.rhinitis.projectrhinitis.member.entity.MemberStatus;
import com.rhinitis.projectrhinitis.member.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomDsl customDsl;
    private final CustomLogoutHandler customLogoutHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .apply(customDsl);

        http
                .authorizeHttpRequests(auth -> {
                    auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/posts/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/members/v1/join"),
                                AntPathRequestMatcher.antMatcher("/members/v1/login")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/members/v1/activation/send"),
                                AntPathRequestMatcher.antMatcher("/members/v1/activation")).hasAuthority(MemberStatus.IN_REGISTER.name())
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/posts/v1/**")).hasAnyAuthority(Role.ADMIN.name(),Role.MEMBER.name(),Role.MANAGER.name())
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/comments/v1/**")).hasAnyAuthority(Role.ADMIN.name(),Role.MEMBER.name(),Role.MANAGER.name())
//                            .anyRequest().permitAll();
                        .anyRequest().authenticated();
                })
                .apply(customDsl);

        http
                .logout(logout ->
                    logout
                            .logoutUrl("/members/v1/logout")
                            .addLogoutHandler(customLogoutHandler)
                            .logoutSuccessHandler(customLogoutSuccessHandler)
                );
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
