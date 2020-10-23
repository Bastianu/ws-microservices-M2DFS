package com.ecommerce.microcommerce.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CbProductController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/Produit/{id}", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackProduct")
    public String getProductById(@PathVariable int id)
    {

        String response = restTemplate.exchange("http://localhost:9090/Produit/{id}",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, id).getBody();

        System.out.println("Response Body " + response);

        return "Product Id -  " + id + " [ Product Details " + response+" ]";
    }

    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackProducts")
    public String getProducts()
    {

        String response = restTemplate.exchange("http://localhost:9090/Produits",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();

        System.out.println("Response Body " + response);

        return "[ Product Details " + response+" ]";
    }

    @RequestMapping(value = "/ProduitsByName", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackProducts")
    public String getProductOrderByName()
    {

        String response = restTemplate.exchange("http://localhost:9090/ProduitsOrderByName",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();

        System.out.println("Response Body " + response);

        return "[ Product Details " + response+" ]";
    }


    @RequestMapping(value = "/AdminProduits", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallbackProducts")
    public String getMargeProduits()
    {

        String response = restTemplate.exchange("http://localhost:9090/AdminProduits",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();

        System.out.println("Response Body " + response);

        return response;
    }

    public String  fallbackProduct(int id){

        return "Fallback response:: No product details available temporarily";
    }

    public String  fallbackProducts(){

        return "Fallback response:: No products details available temporarily";
    }

    @Bean
    //@LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}