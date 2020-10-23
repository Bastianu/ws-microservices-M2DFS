package com.ecommerce.microcommerce.web.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import static org.mockito.Mockito.*;
class ProductControllerTest {
    @Mock
    com.ecommerce.microcommerce.dao.ProductDao productDao;
    @InjectMocks
    com.ecommerce.microcommerce.web.controller.ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testListeProduits(){
        org.springframework.http.converter.json.MappingJacksonValue result = productController.listeProduits();
        Assertions.assertEquals(null, result);
    }

    @Test
    void testAfficherUnProduit(){
        when(productDao.findById(anyInt())).thenReturn(new com.ecommerce.microcommerce.model.Product(0, "nom", 0, 0));

        com.ecommerce.microcommerce.model.Product result = productController.afficherUnProduit(0);
        Assertions.assertEquals(new com.ecommerce.microcommerce.model.Product(0, "nom", 0, 0), result);
    }

    @Test
    void testAjouterProduit(){
        org.springframework.http.ResponseEntity<java.lang.Void> result = productController.ajouterProduit(new com.ecommerce.microcommerce.model.Product(0, null, 0, 0));
        Assertions.assertEquals(null, result);
    }

    @Test
    void testSupprimerProduit(){
        productController.supprimerProduit(0);
    }

    @Test
    void testUpdateProduit(){
        productController.updateProduit(new com.ecommerce.microcommerce.model.Product(0, null, 0, 0));
    }

    @Test
    void testCalculerMargeProduit(){
        java.lang.String result = productController.calculerMargeProduit();
        Assertions.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    void testTrierProduitsParOrdreAlphabetique(){
        when(productDao.findByOrderByNomAsc()).thenReturn(java.util.Arrays.<com.ecommerce.microcommerce.model.Product>asList(new com.ecommerce.microcommerce.model.Product(0, "nom", 0, 0)));

        java.util.List<com.ecommerce.microcommerce.model.Product> result = productController.trierProduitsParOrdreAlphabetique();
        Assertions.assertEquals(java.util.Arrays.<com.ecommerce.microcommerce.model.Product>asList(new com.ecommerce.microcommerce.model.Product(0, "nom", 0, 0)), result);
    }

    @Test
    void testTesteDeRequetes(){
        when(productDao.chercherUnProduitCher(anyInt())).thenReturn(java.util.Arrays.<com.ecommerce.microcommerce.model.Product>asList(new com.ecommerce.microcommerce.model.Product(0, "nom", 0, 0)));

        java.util.List<com.ecommerce.microcommerce.model.Product> result = productController.testeDeRequetes(0);
        Assertions.assertEquals(java.util.Arrays.<com.ecommerce.microcommerce.model.Product>asList(new com.ecommerce.microcommerce.model.Product(0, "nom", 0, 0)), result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme