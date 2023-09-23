package com.danielcode.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(path = "api/v1/customers", method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @RequestMapping(path = "api/v1/customers/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("id") Integer id){
        return customerService.getCustomerById(id);
    }
}
