package com.example.restful_api.service;

import com.example.restful_api.model.Product;
import com.example.restful_api.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class) // configura corretamente os Mocks para testes Isolados
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

    @Test
    @DisplayName("list products should return a list of products")
    void listProducts_returnsListOfProducts(){

        List<Product> products = productService.listProducts();

        Assertions.assertThat(products)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
    }

}