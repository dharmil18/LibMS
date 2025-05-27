package com.libms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@OpenAPIDefinition(info = @Info(title = "Library Management System API", version = "1.0", description = "API for managing library books and members"))
public class LibMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibMsApplication.class, args);
    }

}
