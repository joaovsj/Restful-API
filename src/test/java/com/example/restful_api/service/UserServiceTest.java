package com.example.restful_api.service;

import com.example.restful_api.exception.ItemNotFoundException;
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
import org.springframework.security.authentication.BadCredentialsException;
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
        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
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
    @DisplayName("authenticate works for the correct credentials")
    void authenticate_returnsUser() {
        String name = "Tenza";
        String password = "12345";
        String encryptedPassword = "encrypted";

        BDDMockito.when(passwordEncoder.matches("12345", encryptedPassword))
                .thenReturn(true);

        User userAuthenticated = userService.authenticate(name, password);

        Assertions.assertThat(userAuthenticated).isNotNull();
        Assertions.assertThat(userAuthenticated.getUsername()).isEqualTo(name);
    }

    @Test
    @DisplayName("authenticate method throws ItemNotFoundException when name is invalid")
    void authenticate_throwItemNotFoundException() {
        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenThrow(ItemNotFoundException.class);
        Assertions.assertThatExceptionOfType(ItemNotFoundException.class)
                .isThrownBy(() -> userService.authenticate("Romario", "Da leste"));
    }

    @Test
    @DisplayName("authenticate method throws BadCredetialsException when data are invalid")
    void authenticate_throwsBadCredentialsException() {

        BDDMockito.when(passwordEncoder.matches("invalidPassword", "encryptedPassword"))
            .thenThrow(BadCredentialsException.class);

        Assertions.assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> userService.authenticate("romarinhho", "invalidPassword"));
    }
}