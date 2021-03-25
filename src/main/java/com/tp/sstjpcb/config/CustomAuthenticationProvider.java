package com.tp.sstjpcb.config;

import com.tp.sstjpcb.model.User;
import com.tp.sstjpcb.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserRepo userRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();
        if (!(credentials instanceof String)) {
            return null;
        }
        String password = credentials.toString();
        Optional<User> optionalUser = userRepo.findByEmail(username);

        boolean invalidUser = false;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            boolean passwordMatches = bCryptPasswordEncoder.matches(password, user.getPassword());
            if(!passwordMatches){
                invalidUser = true;
            }
        } else {
            invalidUser = true;
        }
        if(invalidUser) throw new BadCredentialsException("Authentication failed for " + username);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(optionalUser.get().getRole()));

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
        System.out.println(auth);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
