package com.example.userservice.service;

import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.model.Rol;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setEmail("juan@example.com");
        user.setRol(Rol.CLIENTE);

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("Juan", createdUser.getNombre());
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setId("1");
        user.setNombre("Juan");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById("1");

        assertNotNull(foundUser);
        assertEquals("Juan", foundUser.get().getNombre());
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById("1"));
    }
}