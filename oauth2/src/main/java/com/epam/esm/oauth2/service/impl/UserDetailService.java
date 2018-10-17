package com.epam.esm.oauth2.service.impl;


import com.epam.esm.oauth2.service.dto.CustomUserDetails;
import com.epam.esm.oauth2.repository.domain.User;
import com.epam.esm.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The type User detail service.
 */
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", username));
        }
        User user = userOptional.get();
        return new CustomUserDetails(user);
    }
}
