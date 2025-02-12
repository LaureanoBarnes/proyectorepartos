package com.example.authservice.util;

import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;

public class KeycloackProvider {

    private static final String SERVER_URL = "http://localhost:8081";
    private static final String REALM_NAME = "delivery-system";
    private static final String REALM_MASTER = "master";
    private static final String ADMIN_CLI="admin-cli";
    private static final String USER_CONSOLE="admin";
    private static final String PASSWORD_CONSOLE="admin";
    private static final String CLIENT_SECRET="A9P03pfpJsqYcfGcSTuKRWuFbXg0g4PB";

    public static RealmResource getRealmResource() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(SERVER_URL)
                .realm(REALM_MASTER)
                .clientId(ADMIN_CLI)
                .clientSecret(CLIENT_SECRET)
                .username(USER_CONSOLE)
                .password(PASSWORD_CONSOLE)
                .resteasyClient(new ResteasyClientBuilderImpl()
                        .connectionPoolSize(10)
                        .build())
                .build();

        return keycloak.realm(REALM_NAME);
    }

    public static UsersResource getUserResource() {
        RealmResource realmResource = getRealmResource();
        return realmResource.users();
    }
}

