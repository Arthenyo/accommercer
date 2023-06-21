package com.arthenyo.accommerce.controllers;

import com.arthenyo.accommerce.DTO.CategoryDTO;
import com.arthenyo.accommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> dto = categoryService.findAll();
        return ResponseEntity.ok(dto);
    }
}
