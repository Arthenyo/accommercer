package com.arthenyo.accommerce.repositories;

import com.arthenyo.accommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
