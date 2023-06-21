package com.arthenyo.accommerce.services;

import com.arthenyo.accommerce.DTO.CategoryDTO;
import com.arthenyo.accommerce.entities.Category;
import com.arthenyo.accommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }
}
