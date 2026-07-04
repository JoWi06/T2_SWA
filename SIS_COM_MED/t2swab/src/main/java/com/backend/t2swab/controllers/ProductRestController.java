package com.backend.t2swab.controllers;

import com.backend.t2swab.models.Product;
import com.backend.t2swab.models.Category;
import com.backend.t2swab.models.Family;
import com.backend.t2swab.models.Laboratory;
import com.backend.t2swab.repository.ProductRepository;
import com.backend.t2swab.repository.CategoryRepository;
import com.backend.t2swab.repository.FamilyRepository;
import com.backend.t2swab.repository.LaboratoryRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;

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
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) {
        try {
            // 1. Validar y rescatar las relaciones reales de la base de datos
            Category category = categoryRepository.findById(product.getCategory().getIdCategory())
                    .orElseThrow(() -> new RuntimeException("La categoría especificada con ID " + product.getCategory().getIdCategory() + " no existe."));

            Family family = familyRepository.findById(product.getFamily().getIdFamily())
                    .orElseThrow(() -> new RuntimeException("La familia especificada con ID " + product.getFamily().getIdFamily() + " no existe."));

            Laboratory laboratory = laboratoryRepository.findById(product.getLaboratory().getIdLaboratory())
                    .orElseThrow(() -> new RuntimeException("El laboratorio especificado con ID " + product.getLaboratory().getIdLaboratory() + " no existe."));

            // 2. Vincular los objetos persistidos completos al producto antes de guardar
            product.setCategory(category);
            product.setFamily(family);
            product.setLaboratory(laboratory);

            Product savedProduct = productRepository.save(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Devuelve un error 400 Bad Request amigable si falta algún ID en la BD
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ACTUALIZAR UN PRODUCTO EXISTENTE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    try {
                        // 1. Validar las relaciones para la actualización
                        Category category = categoryRepository.findById(productDetails.getCategory().getIdCategory())
                                .orElseThrow(() -> new RuntimeException("La categoría especificada no existe."));
                        Family family = familyRepository.findById(productDetails.getFamily().getIdFamily())
                                .orElseThrow(() -> new RuntimeException("La familia especificada no existe."));
                        Laboratory laboratory = laboratoryRepository.findById(productDetails.getLaboratory().getIdLaboratory())
                                .orElseThrow(() -> new RuntimeException("El laboratorio especificado no existe."));

                        // 2. Modificar los campos básicos
                        existingProduct.setName(productDetails.getName());
                        existingProduct.setDescription(productDetails.getDescription());
                        existingProduct.setPresentation(productDetails.getPresentation());
                        existingProduct.setExpired(productDetails.getExpired());
                        existingProduct.setUnitPrice(productDetails.getUnitPrice());
                        existingProduct.setStock(productDetails.getStock());

                        // 3. Modificar las relaciones completas
                        existingProduct.setCategory(category);
                        existingProduct.setFamily(family);
                        existingProduct.setLaboratory(laboratory);

                        Product updatedProduct = productRepository.save(existingProduct);
                        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

                    } catch (RuntimeException e) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
                    }
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