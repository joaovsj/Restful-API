package com.example.restful_api.service;

import com.example.restful_api.model.User;
import com.example.restful_api.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("Tenza");
        user.setPassword("encrypted");

        BDDMockito.when(passwordEncoder.encode(ArgumentMatchers.anyString())).thenReturn("encrypted");
        BDDMockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
//        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
//        BDDMockito.when(passwordEncoder.matches(ArgumentMatchers.anyString(),ArgumentMatchers.anyString())).thenReturn(true);
    }

    @Test
    @DisplayName("register should save User")
    void register_returnsUserSaved() {
        String name = "Tenza";
        String password = "12345";
        String encryptedPassword = "encrypted";

        User userAuthenticated = userService.register(name, password);
        Assertions.assertThat(userAuthenticated.getUsername()).isEqualTo(name);
        Assertions.assertThat(encryptedPassword).isEqualTo(userAuthenticated.getPassword());
    }

    @Test
    void authenticate_returnsUser() {
        String name = "Tenza";
        String password = "12345";

        User user = userService.authenticate(name, password);

        Assertions.assertThat(user).isNotNull();
    }
}