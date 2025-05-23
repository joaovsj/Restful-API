package com.example.restful_api.integration;


import com.example.restful_api.model.User;
import com.example.restful_api.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // always uses a ramdom port
@AutoConfigureTestDatabase // uses h2
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Log4j2
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @BeforeEach
    void setUp(){
        this.baseUrl = "http://localhost:"+port+"/auth";
    }

    @Test
    void shouldResgisterSucessfully(){

        Map<String, String> user = Map.of("username", "john", "password", "12345");
        ResponseEntity<User> request = restTemplate.postForEntity(baseUrl+"/register", user, User.class);

        Assertions.assertThat(request.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(userRepository.findByUsername("john")).isPresent();
    }
}
