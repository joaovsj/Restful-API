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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UserDetailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailService userDetailsService;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("Tenza");
        user.setPassword("hashPassword");

        BDDMockito.when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("loadByUserName method should return valid User")
    void loadByUserName_returnsUser() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("Tenza");

        Assertions.assertThat(userDetails).isNotNull();
        Assertions.assertThat(userDetails.getUsername()).isEqualTo("Tenza");
    }
}