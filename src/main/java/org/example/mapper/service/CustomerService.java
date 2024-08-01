package org.example.mapper.service;

import org.example.mapper.model.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer getCustomer(Integer id);
}
