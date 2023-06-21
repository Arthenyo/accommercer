package com.arthenyo.accommerce.services;

import com.arthenyo.accommerce.DTO.*;
import com.arthenyo.accommerce.entities.*;
import com.arthenyo.accommerce.enuns.OrderStatus;
import com.arthenyo.accommerce.repositories.OrderItemRepository;
import com.arthenyo.accommerce.repositories.OrderRepository;
import com.arthenyo.accommerce.repositories.ProductRepository;
import com.arthenyo.accommerce.services.excptions.ForbiddenExcption;
import com.arthenyo.accommerce.services.excptions.ResouceNotFoundExcption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundExcption("Recurso não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){
        Order order =new Order();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMANT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO: dto.getItens()){
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem orderItem = new OrderItem(order,product,itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(orderItem);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());
        return new OrderDTO(order);
    }
}
