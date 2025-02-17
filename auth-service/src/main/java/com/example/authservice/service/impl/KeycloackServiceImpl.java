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
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KeycloackServiceImpl implements IKeycloackService {

    @Override
    public List<UserRepresentation> findAllUsers() {
        return KeycloackProvider.getRealmResource()
                .users().list();
    }

    @Override
    public List<UserRepresentation> findallUsers() {
        return List.of();
    }

    @Override
    public List<UserRepresentation> searchUserByUsername(String username) {
        return KeycloackProvider.getRealmResource()
                .users()
                .searchByUsername(username, true);
    }

    @Override
    public String createUser(UserDTO userDTO) {
        UsersResource usersResource = KeycloackProvider.getUserResource();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userDTO.getPassword());
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        // Lógica para asignar roles según el UserDTO
        Set<String> rolesToAssign;
        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
            // Si no hay roles, asigna el rol por defecto de cliente.
            rolesToAssign = Set.of("cliente");
        } else {
            // Si hay roles, transforma la lista de roles a un Set<String> con los nombres exactos,
            // asegurándote de usar la nomenclatura que Keycloak tiene definida.
            rolesToAssign = KeycloackProvider.getRealmResource()
                    .roles()
                    .list()
                    .stream()
                    .filter(role -> userDTO.getRoles()
                            .stream()
                            .anyMatch(roleName -> roleName.equalsIgnoreCase(role.getName())))
                    .map(RoleRepresentation::getName)
                    .collect(Collectors.toSet());
        }

        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
            assignRolesToUser(userId, rolesToAssign);  // Asigna los roles al usuario
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
        UsersResource usersResource = KeycloackProvider.getUserResource();

        // Obtén de Keycloak la lista de roles que coinciden con los nombres en el Set
        List<RoleRepresentation> rolesRepresentation = KeycloackProvider.getRealmResource()
                .roles()
                .list()
                .stream()
                .filter(role -> roles.contains(role.getName()))
                .collect(Collectors.toList());

        // Asigna los roles al usuario
        usersResource.get(userId).roles().realmLevel().add(rolesRepresentation);
    }
}