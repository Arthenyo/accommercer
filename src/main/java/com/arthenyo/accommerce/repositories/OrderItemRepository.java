package com.arthenyo.accommerce.repositories;


import com.arthenyo.accommerce.entities.OrderItem;
import com.arthenyo.accommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
