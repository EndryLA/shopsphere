package com.shopsphere.shopsphere.domain.services;

import com.shopsphere.shopsphere.domain.models.UserAddress;
import com.shopsphere.shopsphere.repositories.UserAddressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;

    public UserAddressService(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    public UserAddress getUserAddressById(int addressId) {
        return userAddressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Adresse introuvable"));
    }


    public UserAddress createUserAddress(UserAddress userAddress) {
        return userAddressRepository.save(userAddress);
    }

    public UserAddress updateUseraddress(UserAddress userAddress) {

        UserAddress userAddressToUpdate = userAddressRepository.findById(userAddress.getId())
                .orElseThrow(() -> new EntityNotFoundException("Adresse introuvable"));

        userAddressToUpdate.setCity(userAddress.getCity());
        userAddressToUpdate.setStreet(userAddress.getStreet());
        userAddressToUpdate.setCountry(userAddress.getCountry());
        userAddressToUpdate.setPostalCode(userAddress.getPostalCode());
        userAddressToUpdate.setPhoneNumber(userAddress.getPhoneNumber());


        return userAddressRepository.save(userAddressToUpdate);
    }

    public void deleteUserAddress(int userAddressId) {
        userAddressRepository.deleteById(userAddressId);
    }

}
