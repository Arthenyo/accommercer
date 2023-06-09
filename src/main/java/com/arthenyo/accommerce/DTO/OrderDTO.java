package com.arthenyo.accommerce.DTO;

import com.arthenyo.accommerce.entities.Order;
import com.arthenyo.accommerce.entities.OrderItem;
import com.arthenyo.accommerce.enuns.OrderStatus;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private Instant moment;
    private OrderStatus status;
    private ClientDTO client;
    private PaymentDTO payment;
    @NotEmpty(message = "deve ter pelo menos um item")
    private List<OrderItemDTO>itens = new ArrayList<>();

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.status = entity.getStatus();
        this.client = new ClientDTO(entity.getClient());
        this.payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());
        for(OrderItem item: entity.getItems()){
            OrderItemDTO itemDTO = new OrderItemDTO(item);
            itens.add(itemDTO);
        }
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public List<OrderItemDTO> getItens() {
        return itens;
    }
    public Double getTotal(){
        double soma = 0;
        for (OrderItemDTO item : itens){
            soma += item.getSubTotal();
        }
        return soma;
    }
}
