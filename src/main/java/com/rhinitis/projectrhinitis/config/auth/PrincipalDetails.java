package com.rhinitis.projectrhinitis.config.auth;

import com.rhinitis.projectrhinitis.member.entity.Member;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Data
public class PrincipalDetails implements UserDetails {
    private Member member;

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("getAuthorities");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> member.getMemberRole().name());
        return authorities;
    }

    public Member getMember() {
        log.info("getMember");
        return member;
    }

    public PrincipalDetails(Authentication authentication) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        this.member = principalDetails.getMember();
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
