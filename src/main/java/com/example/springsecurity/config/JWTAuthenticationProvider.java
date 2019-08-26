package com.example.springsecurity.config;

import com.example.springsecurity.util.JWTUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JWTAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JWTAuthenticationToken token = JWTAuthenticationToken.class.cast(authentication);
        String principal = (String) token.getPrincipal();
        JWTAuthenticationToken authenticatedToken = null;
        Map decode = null;
        try {
            decode = JWTUtil.decode(principal);
            String authority = (String) decode.get("authority");
            String[] split = authority.split(",");
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            for (String s : split) {
                authorities.add(new SimpleGrantedAuthority(s));
            }
            authenticatedToken = new JWTAuthenticationToken(authorities);
            String subject = (String) decode.get("sub");
            authenticatedToken.setUsername(subject);
            authenticatedToken.setAuthenticated(true);
        } catch (IOException e) {
            e.printStackTrace();
        }



        return authenticatedToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
