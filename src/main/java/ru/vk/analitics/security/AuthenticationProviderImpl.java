package ru.vk.analitics.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.vk.analitics.data.User;
import ru.vk.analitics.data.dao.UserRepositoty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivan on 12.07.17.
 */

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserRepositoty userRepositoty;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepositoty.findByLogin(authentication.getName());
        if (user == null || !authentication.getName().equals(user.getLogin())) {
            throw new BadCredentialsException("User not found.");
        }
        if (!user.getPassword().equals(authentication.getCredentials().toString())) {
            throw new BadCredentialsException("Wrong password or username.");
        }
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UsernamePasswordAuthenticationToken(user.getLogin(), authentication.getCredentials().toString(), authorities);
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return authenticationClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
