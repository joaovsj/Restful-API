package com.example.restful_api.service;

import com.example.restful_api.exception.ItemNotFoundException;
import com.example.restful_api.model.Product;
import com.example.restful_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> listProducts(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Product with id "+ id +" not found."));
    }

    public Product save(Product product){
        return productRepository.save(product);
    }
    public void deleteById(Long id){

        if(!productRepository.existsById(id)){
            throw new ItemNotFoundException("Product with id "+ id +" not found.");
        }

        productRepository.deleteById(id);
    }


}
