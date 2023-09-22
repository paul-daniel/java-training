package com.danielcode.customer;

import com.danielcode.Main;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDataAccessService customerDataAccessService;
    private final Main.Foo foo;

    public CustomerService(CustomerDataAccessService customerDataAccessService, Main.Foo foo) {
        this.customerDataAccessService = customerDataAccessService;
        this.foo = foo;
     }

    List<Customer> getAllCustomers(){
        return customerDataAccessService.selectAllCustomers();
    }

    Customer getCustomerById(Integer id){
        Optional<Customer> customer = customerDataAccessService.selectCustomerById(id);
        return customer.orElseThrow(() -> new IllegalArgumentException("Customer does not exist"));
    }
}
