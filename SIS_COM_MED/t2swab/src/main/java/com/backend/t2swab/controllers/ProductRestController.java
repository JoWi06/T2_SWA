package com.backend.t2swab.controllers;

import com.backend.t2swab.models.Product;
import com.backend.t2swab.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

    // LISTAR TODOS LOS PRODUCTOS
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // BUSCAR UN PRODUCTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // CREAR / GUARDAR UN NUEVO PRODUCTO
    @PostMapping
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // ACTUALIZAR UN PRODUCTO EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Validated @RequestBody Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setDescription(productDetails.getDescription());
                    existingProduct.setPresentation(productDetails.getPresentation());
                    existingProduct.setExpired(productDetails.getExpired());
                    existingProduct.setUnitPrice(productDetails.getUnitPrice());
                    existingProduct.setStock(productDetails.getStock());

                    // Relaciones completas
                    existingProduct.setCategory(productDetails.getCategory());
                    existingProduct.setFamily(productDetails.getFamily());
                    existingProduct.setLaboratory(productDetails.getLaboratory());

                    Product updatedProduct = productRepository.save(existingProduct);
                    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // ELIMINAR UN PRODUCTO
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}