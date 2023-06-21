package com.arthenyo.accommerce.repositories;

import com.arthenyo.accommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
