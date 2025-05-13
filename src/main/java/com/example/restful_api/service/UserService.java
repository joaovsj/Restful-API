package com.example.restful_api.service;

import com.example.restful_api.exception.ItemNotFoundException;
import com.example.restful_api.model.User;
import com.example.restful_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String password){

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public User authenticate(String username, String password){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("User not found"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException("Credenciais inválidas");
        }
        return user;
    }
}
