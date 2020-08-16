package com.oss.pii.demo.service;

import com.oss.pii.demo.entity.Customer;
import com.oss.pii.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public List<Customer> getAll(){
        return repository.findAll();
    }

    public Customer saveCustomer(Customer customer){
        return repository.save(customer);
    }
}
