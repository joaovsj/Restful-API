package com.example.restful_api.service;

import com.example.restful_api.model.Product;
import com.example.restful_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp(){

        Product product = new Product(1L, "Milk", 5D);

        BDDMockito.when(productRepository.findAll()).thenReturn(List.of(product));
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));
        BDDMockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
        BDDMockito.doNothing().when(productRepository).deleteById(ArgumentMatchers.anyLong());
    }

}