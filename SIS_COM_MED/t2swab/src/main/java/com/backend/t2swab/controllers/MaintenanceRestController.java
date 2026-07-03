package com.backend.t2swab.controllers;

import com.backend.t2swab.models.Category;
import com.backend.t2swab.models.Family;
import com.backend.t2swab.models.Laboratory;
import com.backend.t2swab.repository.CategoryRepository;
import com.backend.t2swab.repository.FamilyRepository;
import com.backend.t2swab.repository.LaboratoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MaintenanceRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private LaboratoryRepository laboratoryRepository;

    // GET: http://localhost:8080/api/categories
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // GET: http://localhost:8080/api/families
    @GetMapping("/families")
    public List<Family> getAllFamilies() {
        return familyRepository.findAll();
    }

    // GET: http://localhost:8080/api/laboratories
    @GetMapping("/laboratories")
    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }
}