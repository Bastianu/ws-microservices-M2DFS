package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Mock
    ProductDao productDao;
    @InjectMocks
    ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAfficherUnProduit() {
        when(productDao.findById(anyInt())).thenReturn(new Product(0, "nom", 0, 0));

        Product result = productController.afficherUnProduit(0);
        Product p = new Product(0, "nom", 0, 0);
        Assertions.assertTrue(p.getId() == result.getId());
        Assertions.assertTrue(p.getNom().equals(p.getNom()));
        Assertions.assertTrue(p.getPrix() == result.getPrix());
        Assertions.assertTrue(p.getPrixAchat() == result.getPrixAchat());
    }

    @Test
    void testAjouterProduit() throws ProduitGratuitException {
        ResponseEntity<Void> result = productController.ajouterProduit(new Product(0, null, 10, 0));
        Assertions.assertTrue(result.getStatusCodeValue() >= 200);
        Assertions.assertTrue( result.getStatusCodeValue() < 300);
    }

    @Test
    public void testAjoutMauvaisProduit() throws ProduitGratuitException {
        Product p = new Product(1,"Ordinateur portable gratuit", 0,120);
        Assertions.assertThrows(ProduitGratuitException.class, () -> {
            productController.ajouterProduit(p);
        });
    }

    @Test
    void testSupprimerProduit() {
        productController.supprimerProduit(0);
    }

    @Test
    void testUpdateProduit() throws ProduitGratuitException {
        productController.updateProduit(new Product(0, null, 10, 0));
    }

    @Test
    public void testUpdateMauvaisProduit() throws ProduitGratuitException {
        Product p = new Product(1,"Ordinateur portable gratuit", 0,120);
        Assertions.assertThrows(ProduitGratuitException.class, () -> {
            productController.updateProduit(p);
        });
    }

    @Test
    void testTrierProduitsParOrdreAlphabetique() {
        when(productDao.findByOrderByNomAsc()).
                thenReturn(Arrays.<Product>asList(
                        new Product(1, "E", 10, 0),
                        new Product(2, "Z", 10, 0),
                        new Product(3, "R", 10, 0)));

        List<Product> result = productController.trierProduitsParOrdreAlphabetique();
        Assertions.assertEquals(1, result.get(0).getId());
    }

    @Test
    void testTesteDeRequetes() throws ProduitGratuitException {
        when(productDao.chercherUnProduitCher(anyInt())).
                thenReturn(Arrays.<Product>asList(new Product(0, "nom", 10, 0)));

        List<Product> result = productController.testeDeRequetes(9);
        Assertions.assertEquals(Arrays.<Product>asList(new Product(0, "nom", 10, 0)).get(0).getId(), result.get(0).getId());

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme