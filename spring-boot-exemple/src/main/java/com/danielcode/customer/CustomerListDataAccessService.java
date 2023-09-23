package com.danielcode.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CustomerListDataAccessService implements CustomerDao{

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1,"Alex", "alex@gmail.com", 21);
        Customer claude = new Customer(2,"Claude", "claude@gmail.com", 25);
        customers.addAll(List.of(alex, claude));
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(c-> Objects.equals(c.getId(), id))
                .findFirst();
    }
}
