package com.shopsphere.shopsphere.domain.services;

import com.shopsphere.shopsphere.domain.models.Authority;
import com.shopsphere.shopsphere.domain.models.User;
import com.shopsphere.shopsphere.repositories.AuthorityRepository;
import com.shopsphere.shopsphere.repositories.UserRepository;
import com.shopsphere.shopsphere.shared.dtos.UserDTO;
import com.shopsphere.shopsphere.shared.mappers.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }



    public Page<UserDTO> getUsers(int page, int size, Sort sort){
        Pageable pageable = PageRequest.of(page, size, sort);

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

        user.setPassword(hashedPassword);

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
