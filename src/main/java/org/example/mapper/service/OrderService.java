package org.example.mapper.service;

import org.example.mapper.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Integer orderId);
    Order createOrder(Order order);
    Order updateOrder(Integer id, Order order);
    void deleteOrder(Integer orderId);
}
