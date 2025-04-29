package com.example.restful_api.service;

import com.example.restful_api.model.User;
import com.example.restful_api.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User register(String username, String password){

        PasswordEncoder encoder = getPasswordEncoder();
        String passwordCrypted  = encoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(passwordCrypted)
                .build();

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    private PasswordEncoder getPasswordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }




}
