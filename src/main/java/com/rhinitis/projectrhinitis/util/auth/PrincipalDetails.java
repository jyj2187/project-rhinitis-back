package com.rhinitis.projectrhinitis.util.auth;

import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.entity.Role;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Data
public class PrincipalDetails implements UserDetails {
    private final Member member;

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("getAuthorities");
        return Arrays.stream(Role.values()).map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList());
    }

    public Member getMember() {
        log.info("getMember");
        return member;
    }

    @Override
    public String getPassword() {
        log.info("getPassword");
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        log.info("getUsername");
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
