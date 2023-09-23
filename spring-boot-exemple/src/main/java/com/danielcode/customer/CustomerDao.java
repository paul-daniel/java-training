package com.danielcode.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    void deleteCustomerById(Integer id);
    void updateCustomerInfos(Customer customer);
    boolean existsCustomerWithId(Integer id);
    boolean existsPersonWithEmail(String email);
}
