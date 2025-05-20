package com.example.restful_api.controller;

import com.example.restful_api.model.Product;
import com.example.restful_api.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Coffee");
        product.setPrice(31D);

        BDDMockito.when(productService.listProducts()).thenReturn(List.of(product));
        BDDMockito.when(productService.findById(1L)).thenReturn(product);
        BDDMockito.when(productService.save(ArgumentMatchers.any(Product.class))).thenReturn(product);
        BDDMockito.doNothing().when(productService).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List all products")
    void list_shouldReturnAllProducts(){

        List<Product> products = productController.list().getBody();
        Assertions.assertThat(products).isNotNull().isNotEmpty();
        Assertions.assertThat(products.size()).isEqualTo(1);
        Assertions.assertThat(products.get(0).getName()).isEqualTo("Coffee");
    }

    @Test
    @DisplayName("FindById should return the right product")
    void findById_shouldReturnProduct(){
        ResponseEntity<?> productResponse = productController.findById(1L);
        Assertions.assertThat(productResponse.getBody()).isNotNull();
        Assertions.assertThat(productResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("create should save the product")
    void create_shouldReturnProduct(){

        Product product = new Product();
        product.setName("Coffee");
        product.setPrice(31D);

        ResponseEntity<Product> productResponse = productController.create(product);
        Assertions.assertThat(productResponse.getBody()).isNotNull();
        Assertions.assertThat(productResponse.getBody().getName()).isEqualTo("Coffee");
        Assertions.assertThat(productResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


}