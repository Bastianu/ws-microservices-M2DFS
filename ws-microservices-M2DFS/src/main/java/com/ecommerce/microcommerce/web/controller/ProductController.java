package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "erreur auth"),
            @ApiResponse(code = 403, message = "erreur accès"),
            @ApiResponse(code = 404, message = "page pas trouvée")
    })


    //Récupérer la liste des produits
    @ApiOperation(value = "récupère la liste des produits dans la bdd", response = Iterable.class, tags ="getAllProducts")
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {
        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);
        return produitsFiltres;
    }


    //Récupérer un produit par son Id
    @ApiOperation(value = "récupère un produit par son id", response = Iterable.class, tags ="getProductById")
    @RequestMapping(value = "/Produit/{id}", method = RequestMethod.GET)
    public Product afficherUnProduit(@PathVariable(value = "id") int id) {

        return productDao.findById(id);
    }




    //ajouter un produit
    @ApiOperation(value = "ajoute un produit", response = Iterable.class, tags ="addProduct")
    @PostMapping(value = "/Produit")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) throws ProduitGratuitException {

        if(product.getPrix() <= 0) {
            throw new ProduitGratuitException("le prix de vente ne peut pas être égal à 0");
        }

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // supprimer un produit
    @ApiOperation(value = "delete produit par son id", response = Iterable.class, tags ="deleteProduct")
    @RequestMapping(value = "/Produit/{id}", method = RequestMethod.DELETE)
    public void supprimerProduit(@PathVariable(value = "id") int id) {
        productDao.delete(id);
    }

    // Mettre à jour un produit
    @ApiOperation(value = "update un produit", response = Iterable.class, tags ="updateProduct")
    @RequestMapping(value = "/Produit", method = RequestMethod.PUT)
    public void updateProduit(@RequestBody Product product) throws ProduitGratuitException {
        if(product.getPrix()<=0) {
            throw new ProduitGratuitException("le prix de vente ne peut pas être égal à 0");
        }
        productDao.save(product);

    }

    @ApiOperation(value = "récupère les produits et leur rapport prix/prix acheté", response = Iterable.class, tags ="getProductsWithMarge")
    @RequestMapping(value = "/AdminProduits", method = RequestMethod.GET)
    public String calculerMargeProduit(){

        String result = "<br>";
        List<Product> products = productDao.findAll();
        for(Product p : products){
            result += p.toString() +" : "+ (p.getPrix() - p.getPrixAchat()) +" <br> ";
        }
        return result;
    }

    @ApiOperation(value = "liste des produits classé par nom", response = Iterable.class, tags ="getProductOrderByName")
    @RequestMapping(value = "/ProduitsOrderByName", method = RequestMethod.GET)
    public List<Product> trierProduitsParOrdreAlphabetique(){
        return productDao.findByOrderByNomAsc();
    }


    //Pour les tests
    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {
        return productDao.chercherUnProduitCher(400);
    }



}
