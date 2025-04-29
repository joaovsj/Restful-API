package com.example.restful_api.controller;

import com.example.restful_api.model.User;
import com.example.restful_api.security.JwtUtil;
import com.example.restful_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request){

        User user = userService.register(request.get("username"),request.get("password"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request){

        Optional<User> user = userService.findByUsername(request.get("username"));

        if (user.isPresent() && user.get().getPassword().equals(request.get("password"))){

            String token = JwtUtil.generateToken(user.get().getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
