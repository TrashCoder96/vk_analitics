package ru.vk.analitics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.vk.analitics.service.SecurityService;

/**
 * Created by ivan on 12.07.17.
 */

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public void login(String login, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login, password);
        SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(authenticationToken));
    }

    @Override
    public void logout() {

    }
}
