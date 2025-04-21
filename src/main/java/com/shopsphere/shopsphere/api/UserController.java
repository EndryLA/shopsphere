package com.shopsphere.shopsphere.api;

import com.shopsphere.shopsphere.domain.models.User;
import com.shopsphere.shopsphere.domain.services.UserService;
import com.shopsphere.shopsphere.shared.dtos.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<Page<UserDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size)  {
        try {
            Page<UserDTO> users = userService.getUsers(page, size);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue");
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int userId) {
        try {
            UserDTO user = userService.getUser(userId);

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue");
        }
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        System.out.println(user);
        try {
            UserDTO createdUser = userService.createUser(user);

            return new ResponseEntity<>(createdUser,HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue");
        }
    }


    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int userId, @RequestBody User user) {
        try {
            UserDTO updatedUser = userService.updateUser(user, userId);

            return new ResponseEntity<>(updatedUser,HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue");
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId){
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage(),e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue");
        }
    }



}
