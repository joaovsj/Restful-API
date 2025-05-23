package com.example.restful_api.integration;

import com.example.restful_api.model.Product;
import com.example.restful_api.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Log4j2
public class ProductControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    private String productUrl;

    private String token;

    @BeforeEach
    void setUp(){

        String authUrl = "http://localhost:" + port + "/auth";
        productUrl = "http://localhost:" + port + "/api/products";

        Map<String, String> user = Map.of("username", "john", "password", "123456");
        restTemplate.postForEntity(authUrl + "/register", user, Void.class);

        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(authUrl + "/login", user, Map.class);
        token = loginResponse.getBody().get("token").toString();

    }

    @Test
    void create_shouldCreateValidProduct(){

        Product product = new Product();
        product.setName("Coffee");
        product.setPrice(25D);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        ResponseEntity<Product> createProduct = restTemplate.postForEntity(productUrl, request, Product.class);
        Assertions.assertThat(createProduct.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(productRepository.findAll().size()).isEqualTo(1);
    }

}
