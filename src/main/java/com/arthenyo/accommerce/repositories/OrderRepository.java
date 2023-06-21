package com.arthenyo.accommerce.repositories;


import com.arthenyo.accommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
