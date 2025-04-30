package com.example.restful_api.controller;

import com.example.restful_api.model.User;
import com.example.restful_api.security.JwtUtil;
import com.example.restful_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request){

        User user = userService.register(request.get("username"),request.get("password"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){

        User user = userService.authenticate(request.get("username"), request.get("password"));
        String token = JwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of("token", token));

//        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

}
