package com.example.restful_api.repository;

import com.example.restful_api.model.Product;
import com.example.restful_api.util.ProductCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Method save creates Product when sucessful")
    void save_persistProductWhenSuccessful(){

        Product productTobeSaved = ProductCreator.createProduct();
        Product productSaved = productRepository.save(productTobeSaved);

        Assertions.assertNotNull(productSaved);
        Assertions.assertEquals(productSaved.getId(), productTobeSaved.getId());
    }

}