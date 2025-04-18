package com.shopsphere.shopsphere.shared.mappers;

import com.shopsphere.shopsphere.domain.models.Authority;
import com.shopsphere.shopsphere.domain.models.User;
import com.shopsphere.shopsphere.shared.dtos.UserDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO userDTO(User user) {

        Set<String> authorities = user.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toSet());

        UserDTO userDTO = new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getStreet(),
                user.getCity(),
                user.getPostalCode(),
                user.getCountry(),
                user.getPhoneNumber(),
                authorities
        );

        return userDTO;
    }



}
