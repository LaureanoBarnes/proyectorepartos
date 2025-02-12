package com.example.authservice.service.impl;

import com.example.authservice.dto.UserDTO;
import com.example.authservice.service.IKeycloackService;
import com.example.authservice.util.KeycloackProvider;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KeycloackServiceImpl implements IKeycloackService {

    @Override
    public List<UserRepresentation> findAllUsers() {
        // Obtén el recurso de usuarios
        UsersResource usersResource = KeycloackProvider.getUserResource();
        // Retorna todos los usuarios del realm
        return usersResource.list();
    }

    @Override
    public List<UserRepresentation> findallUsers() {
        return List.of();
    }

    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        // Obtén el recurso de usuarios
        UsersResource usersResource = KeycloackProvider.getUserResource();
        // Busca usuarios por nombre de usuario
        return usersResource.search(username);
    }

    @Override
    public String createUser(UserDTO userDTO) {
        // Obtén el recurso de usuarios
        UsersResource usersResource = KeycloackProvider.getUserResource();

        // Configura las credenciales del usuario
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.getPassword());
        credential.setTemporary(false);

        // Configura el usuario
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        // Crea el usuario en Keycloak
        Response response = usersResource.create(user);

        // Verifica si el usuario fue creado exitosamente
        if (response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            assignRolesToUser(userId, userDTO.getRoles()); // Asigna roles al usuario
            return userId;
        } else {
            throw new RuntimeException("Error al crear el usuario: " + response.getStatusInfo().getReasonPhrase());
        }
    }

    @Override
    public void deleteUser(String userId) {
        // Obtén el recurso de usuarios
        UsersResource usersResource = KeycloackProvider.getUserResource();
        // Elimina el usuario por su ID
        usersResource.get(userId).remove();
    }

    @Override
    public void updateUser(String userId, UserDTO userDTO) {
        // Obtén el recurso de usuarios
        UsersResource usersResource = KeycloackProvider.getUserResource();
        UserResource userResource = usersResource.get(userId);

        // Configura el usuario actualizado
        UserRepresentation user = userResource.toRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());

        // Actualiza el usuario en Keycloak
        userResource.update(user);

        // Actualiza los roles del usuario
        assignRolesToUser(userId, userDTO.getRoles());
    }

    private void assignRolesToUser(String userId, Set<String> roles) {
        // Obtén el recurso de usuarios
        UsersResource usersResource = KeycloackProvider.getUserResource();

        // Obtén los roles del realm
        List<RoleRepresentation> realmRoles = KeycloackProvider.getRealmResource()
                .roles()
                .list()
                .stream()
                .filter(role -> roles.contains(role.getName()))
                .collect(Collectors.toList());

        // Asigna los roles al usuario
        usersResource.get(userId).roles().realmLevel().add(realmRoles);
    }
}