package com.ecommerce.microcommerce.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@EnableAutoConfiguration
@Configuration
// activer swagger
@EnableSwagger2

public class SwaggerConfig {
    @Bean
    public Docket api() {

        // implementer cette methode pour le swagger
        return null;
    }
}