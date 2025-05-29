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
import java.util.Objects;

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

    private HttpHeaders headers;


    @BeforeEach
    void setUp() {

        String authUrl = "http://localhost:" + port + "/auth";
        productUrl = "http://localhost:" + port + "/api/products";

        Map<String, String> user = Map.of("username", "john", "password", "123456");
        restTemplate.postForEntity(authUrl + "/register", user, Void.class);

        ResponseEntity<Map> loginResponse = restTemplate.postForEntity(authUrl + "/login", user, Map.class);
        token = loginResponse.getBody().get("token").toString();

        headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void create_shouldCreateValidProduct() {

        Product product = new Product();
        product.setName("Coffee");
        product.setPrice(25D);

        HttpEntity<Product> request = new HttpEntity<>(product, headers);

        ResponseEntity<Product> createProduct = restTemplate.postForEntity(productUrl, request, Product.class);
        Assertions.assertThat(createProduct.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(productRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void list_shouldReturnListOfProducts() {
        Product product = new Product();
        product.setName("Coffee");
        product.setPrice(25D);

        productRepository.save(product);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Product[]> listProducts = restTemplate.exchange(productUrl + "/", HttpMethod.GET, request, Product[].class);
        Assertions.assertThat(listProducts.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(listProducts.getBody()).length).isEqualTo(1);
    }

    @Test
    void findById_shouldReturnAccurateProduct(){
        Product product = new Product(null, "Milk", 6D);
        Product savedProduct = productRepository.save(product);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Product> productResponse = restTemplate.exchange(productUrl + "/" + savedProduct.getId(), HttpMethod.GET, request, Product.class);

        Assertions.assertThat(productResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(productResponse.getBody().getName()).isEqualTo(savedProduct.getName());
    }

    @Test
    void delete_shouldRemoveProduct(){
        Product product = new Product(null, "Meat", 20D);
        Product savedProduct = productRepository.save(product);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Void> content = restTemplate.exchange(productUrl + "/" + savedProduct.getId(), HttpMethod.DELETE, request, Void.class);

        Assertions.assertThat(content.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(content.getBody()).isNull();
        Assertions.assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
    }
}
