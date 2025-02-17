package com.example.authservice.service;

import com.example.authservice.dto.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import java.util.List;

public interface IKeycloackService {
    List<UserRepresentation> findAllUsers();

    List<UserRepresentation> findallUsers();

    List<UserRepresentation> searchUserByUsername(String username);
    String createUser(UserDTO userDTO);
    void deleteUser(String userId);
    void updateUser(String userId, UserDTO userDTO);
}