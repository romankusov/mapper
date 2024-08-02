package org.example.mapper.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mapper.controller.OrderController;
import org.example.mapper.model.Customer;
import org.example.mapper.model.Order;
import org.example.mapper.model.Product;
import org.example.mapper.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class ProductServiceImplTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("1234567890");

        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPrice(100.0);
        product.setQuantityInStock(10);

        order = new Order();
        order.setId(1L);
        order.setCustomer(customer);
        order.setProducts(Collections.singletonList(product));
        order.setOrderDate(new Date());
        order.setShippingAddress("123 Main St");
        order.setTotalPrice(100.0);
        order.setOrderStatus("Pending");
    }

    @Test
    void testOkGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/orders/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(order.getId()))
                .andExpect(jsonPath("$[0].customer.id").value(order.getCustomer().getId()))
                .andExpect(jsonPath("$[0].products[0].id").value(order.getProducts().get(0).getId()))
                .andExpect(jsonPath("$[0].shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$[0].totalPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$[0].orderStatus").value(order.getOrderStatus()));
    }

    @Test
    void testOkGetOrderById() throws Exception {
        when(orderService.getOrderById(anyInt())).thenReturn(order);

        mockMvc.perform(get("/orders/{orderId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.customer.id").value(order.getCustomer().getId()))
                .andExpect(jsonPath("$.products[0].id").value(order.getProducts().get(0).getId()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.totalPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$.orderStatus").value(order.getOrderStatus()));
    }

    @Test
    void testOkPostCreateOrder() throws Exception {
        when(orderService.createOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/orders/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.customer.id").value(order.getCustomer().getId()))
                .andExpect(jsonPath("$.products[0].id").value(order.getProducts().get(0).getId()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.totalPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$.orderStatus").value(order.getOrderStatus()));
    }

    @Test
    void testOkPostUpdateOrder() throws Exception {
        when(orderService.updateOrder(anyInt(), any(Order.class))).thenReturn(order);

        mockMvc.perform(put("/orders/{orderId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.customer.id").value(order.getCustomer().getId()))
                .andExpect(jsonPath("$.products[0].id").value(order.getProducts().get(0).getId()))
                .andExpect(jsonPath("$.shippingAddress").value(order.getShippingAddress()))
                .andExpect(jsonPath("$.totalPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$.orderStatus").value(order.getOrderStatus()));
    }

    @Test
    void testOkDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(anyInt());

        mockMvc.perform(delete("/orders/{orderId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).deleteOrder(anyInt());
    }

}