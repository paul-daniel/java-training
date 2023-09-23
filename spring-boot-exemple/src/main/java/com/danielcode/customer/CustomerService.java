package com.danielcode.customer;

import com.danielcode.Main;
import com.danielcode.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
     }

    List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    Customer getCustomerById(Integer id){
        Optional<Customer> customer = customerDao.selectCustomerById(id);
        return customer.orElseThrow(() -> new ResourceNotFound("Customer with id [%s] does not exist".formatted(id)));
    }
}
