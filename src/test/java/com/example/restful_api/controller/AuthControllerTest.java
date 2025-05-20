package com.example.restful_api.controller;

import com.example.restful_api.model.User;
import com.example.restful_api.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp(){
        User user = new User();
        user.setId(1L);
        user.setUsername("Ainz");
        user.setPassword("12345");

        BDDMockito.when(userService.register("Ainz", "12345")).thenReturn(user);
    }

    @Test
    @DisplayName("Method responsible to register user")
    void register_shouldAuthenticateUser(){

        Map<String, String> user = Map.of("username", "Ainz", "password", "12345");
        ResponseEntity<User> userAuthenticated = authController.register(user);

        Assertions.assertThat(userAuthenticated).isNotNull();
        System.out.println(userAuthenticated.getBody());

    }
}