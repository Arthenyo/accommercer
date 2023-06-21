package com.arthenyo.accommerce.services;

import com.arthenyo.accommerce.DTO.OrderDTO;
import com.arthenyo.accommerce.entities.Order;
import com.arthenyo.accommerce.repositories.OrderRepository;
import com.arthenyo.accommerce.services.excptions.ResouceNotFoundExcption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundExcption("Recurso não encontrado"));
        return new OrderDTO(order);
    }
}
