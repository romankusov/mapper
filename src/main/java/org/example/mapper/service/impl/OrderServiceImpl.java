package org.example.mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mapper.exceptionhandling.excceptions.NotFoundException;
import org.example.mapper.model.Customer;
import org.example.mapper.model.Order;
import org.example.mapper.model.Product;
import org.example.mapper.repository.CustomerRepository;
import org.example.mapper.repository.OrderRepository;
import org.example.mapper.repository.ProductRepository;
import org.example.mapper.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundException("Заказ с id " + orderId + " не найден"));
    }

    @Override
    public Order createOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new NotFoundException("Покупатель с id "
                        + order.getCustomer().getId() + " не найден"));
        List <Integer> productsIdsFromReq = order.getProducts().stream().map(Product::getId).toList();
        List<Product> productList = Optional.of(productRepository.findAllById(productsIdsFromReq)).
                orElseThrow(() -> new NotFoundException("Продукты с указанными id не найдены"));
        order.setCustomer(customer);
        order.setProducts(productList);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Integer id, Order order) {
        Order updOrder = orderRepository.findById(id).map(o -> {
            o.setCustomer(order.getCustomer());
            o.setProducts(order.getProducts());
            o.setOrderDate(order.getOrderDate());
            o.setShippingAddress(order.getShippingAddress());
            o.setTotalPrice(order.getTotalPrice());
            o.setOrderStatus(order.getOrderStatus());
            return o;
        }).orElseThrow(() ->
                new NotFoundException("Заказ с id " + id + " не найден"));
        return orderRepository.save(updOrder);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }
}
