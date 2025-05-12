package com.example.restful_api.util;

import com.example.restful_api.model.Product;

public class ProductCreator {

    public static Product createProduct(){
        Product product = new Product();
        product.setName("Coffee");
        product.setPrice(25D);
        return product;
    }


}
