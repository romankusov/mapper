package org.example.mapper.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mapper.exceptionhandling.excceptions.NotFoundException;
import org.example.mapper.model.Customer;
import org.example.mapper.repository.CustomerRepository;
import org.example.mapper.service.CustomerService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Покупатель с id "
                + id + " не найден"));
    }
}
