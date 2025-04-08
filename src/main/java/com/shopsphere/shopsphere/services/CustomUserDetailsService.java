package com.shopsphere.shopsphere.services;

import com.shopsphere.shopsphere.models.User;
import com.shopsphere.shopsphere.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {

            return userRepository.findByEmail();

        } catch (UsernameNotFoundException e) {

            throw new UsernameNotFoundException("Utilisateur introuvable",e);

        }


    }
}
