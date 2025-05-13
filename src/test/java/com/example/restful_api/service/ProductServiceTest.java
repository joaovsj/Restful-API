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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class) // configura o SpringContext para os tests
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
        BDDMockito.when(productRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        BDDMockito.doNothing().when(productRepository).deleteById(ArgumentMatchers.any(Long.class));
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

    @Test
    @DisplayName("findById returns a product")
    void findById_returnsProduct(){
        Product product = productService.findById(1L);

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getName()).isEqualTo("Milk");
    }

    @Test
    @DisplayName("save method persists product and return the same one with ID")
    void save_persistsProduct(){

        Product product = productService.save(new Product(1L, "Milk", 5D));

        Assertions.assertThat(product).isNotNull();
        Assertions.assertThat(product.getName()).isEqualTo("Milk");
    }

    @Test
    @DisplayName("deleteById method removes product")
    void deleteById(){
        Assertions.assertThatCode(() -> productService.deleteById(1L)).doesNotThrowAnyException();
    }

}