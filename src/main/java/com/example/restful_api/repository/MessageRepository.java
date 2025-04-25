package com.example.restful_api.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

    public String getMessage(){
        return "Hello World";
    }
}
