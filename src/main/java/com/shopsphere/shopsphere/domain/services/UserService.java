package com.shopsphere.shopsphere.domain.services;

import com.shopsphere.shopsphere.domain.models.Authority;
import com.shopsphere.shopsphere.domain.models.User;
import com.shopsphere.shopsphere.repositories.AuthorityRepository;
import com.shopsphere.shopsphere.repositories.UserRepository;
import com.shopsphere.shopsphere.shared.dtos.UserDTO;
import com.shopsphere.shopsphere.shared.mappers.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }



    public Page<UserDTO> getUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        Page<User> users = userRepository.findAll(pageable);

        return users.map(UserMapper::userToDTO);
    }

    public UserDTO getUser(int userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("Utilisateur introuvable");
        }

        return UserMapper.userToDTO(user.get());
    }

    public UserDTO createUser(User user) {

        String hashedPassword = passwordEncoder.encode(user.getPassword());

        Set<Authority> userAuthorities = Optional.ofNullable(user.getAuthorities())
                .orElse(new HashSet<>())
                .stream()
                .map(authority -> (Authority) authority)
                .collect(Collectors.toSet());



        Authority defaultAuthority = authorityRepository.findByRole("ROLE_USER");

        if (defaultAuthority == null) {
            defaultAuthority = new Authority();
            defaultAuthority.setRole("ROLE_USER");

            defaultAuthority = authorityRepository.save(defaultAuthority);
        }

        userAuthorities.add(defaultAuthority);
        user.setPassword(hashedPassword);
        user.setAuthorities(userAuthorities);

        User savedUser = userRepository.save(user);

        return UserMapper.userToDTO(savedUser);

    }

    public UserDTO updateUser(User user, int userId) {

        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("Utilisateur introuvable");
        }

        // this is just in case the request has a bad user, to prevent another entry instead of an update :)
        user.setId(userId);
        User updatedUser = userRepository.save(user);

        return UserMapper.userToDTO(updatedUser);

    }

    public void deleteUser(int userId) {

        Optional<User> userToDelete = userRepository.findById(userId);

        if (userToDelete.isEmpty()) {
            throw new EntityNotFoundException("Utilisateur introuvable");
        }

        userRepository.delete(userToDelete.get());
    }

}
