package com.example.userservice.integration;

import com.example.userservice.model.Rol;
import com.example.userservice.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAndGetUser() {
        // Crear un usuario
        User newUser = new User();
        newUser.setNombre("Ana");
        newUser.setApellido("García");
        newUser.setEmail("ana@example.com");
        newUser.setRol(Rol.CLIENTE);

        ResponseEntity<User> postResponse = restTemplate.postForEntity("/user", newUser, User.class);
        assertThat(postResponse.getStatusCodeValue()).isEqualTo(201);
        User createdUser = postResponse.getBody();
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getNombre()).isEqualTo("Ana");

        // Obtener el usuario creado
        ResponseEntity<User> getResponse = restTemplate.getForEntity("/user/" + createdUser.getId(), User.class);
        assertThat(getResponse.getStatusCodeValue()).isEqualTo(200);
        User fetchedUser = getResponse.getBody();
        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getNombre()).isEqualTo("Ana");
    }
}
