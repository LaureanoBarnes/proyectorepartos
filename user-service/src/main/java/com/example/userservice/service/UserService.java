package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.exception.DuplicateEmailException;
import com.example.userservice.exception.ResourceNotFoundException;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO convertToDTO(User user) {
        return new UserDTO(user.getId(), user.getNombre(), user.getApellido(), user.getEmail(), user.getRol(), user.getLatitud(),user.getLongitud());
    }

    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateEmailException("El correo electrónico" + user.getEmail() + " ya está en uso.");
        }
        return userRepository.save(user);
    }

    @Cacheable(value = "users", key = "#id")
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Usuario no encontrado con id: " + id)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        Optional<User> existingUser = userRepository.findByEmail(userDetails.getEmail());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new DuplicateEmailException("El correo electrónico" + userDetails.getEmail() + " ya está en uso.");
        }

        user.setNombre(userDetails.getNombre());
        user.setApellido(userDetails.getApellido());
        user.setEmail(userDetails.getEmail());
        user.setRol(userDetails.getRol());

        return userRepository.save(user);
    }


    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
        userRepository.delete(user);
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::convertToDTO);
    }
}