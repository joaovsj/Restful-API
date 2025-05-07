package com.example.restful_api.repository;

import com.example.restful_api.model.Product;
import com.example.restful_api.util.ProductCreator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Testing ProductRepository")
@Log4j2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Method save creates Product when sucessful")
    void save_persistProductWhenSuccessful(){

        Product productToBeSaved = ProductCreator.createProduct();
        Product productSaved = productRepository.save(productToBeSaved);

        Assertions.assertNotNull(productSaved);
        Assertions.assertEquals(productSaved.getId(), productToBeSaved.getId());
    }

    @Test
    @DisplayName("Find all returns list of Products when sucessful")
    void list_returnListOfProductsWhenSuccessful(){
        Product productToBeSaved = ProductCreator.createProduct();
        productRepository.save(productToBeSaved);

        List<Product> products = productRepository.findAll();

        Assertions.assertNotNull(products);
        Assertions.assertEquals(products.get(0).getName(), productToBeSaved.getName());
        Assertions.assertFalse(products.isEmpty());
    }

    @Test
    @DisplayName("Find all returns Empty list")
    void list_returnsEmptyList(){
        List<Product> products = productRepository.findAll();
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.isEmpty());
    }

    @Test
    @DisplayName("Find by id return the correct product")
    void findById_returnsProduct(){

        Product firstProduct = new Product();
        firstProduct.setName("Milk");
        firstProduct.setPrice(5D);

        Product secondProduct = new Product();
        secondProduct.setName("Oil");
        secondProduct.setPrice(10D);

        Product firstProductSaved = productRepository.save(firstProduct);
        Product secondProductSaved = productRepository.save(secondProduct);

        Optional<Product> productFound = productRepository.findById(secondProductSaved.getId());

        log.info(productFound);
        log.info(secondProduct);

        Assertions.assertNotNull(productFound);
        Assertions.assertEquals(productFound.get().getName(), secondProduct.getName());
    }
}