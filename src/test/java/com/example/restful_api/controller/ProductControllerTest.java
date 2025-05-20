package com.example.restful_api.controller;

import com.example.restful_api.model.Product;
import com.example.restful_api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        BDDMockito.when(productService.save(product)).thenReturn(product);
        BDDMockito.doNothing().when(productService).deleteById(ArgumentMatchers.anyLong());
    }


}