package com.danielcode.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Integer id) {
        return customers.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(c -> customers.remove(c));
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public void updateCustomerInfos(Customer customer) {
        customers = customers.stream()
                .peek(c -> {
                    if(c.getId().equals(customer.getId())){
                        c.setAge(customer.getAge());
                        c.setName(customer.getName());
                        c.setEmail(customer.getEmail());
                    }

                }).collect(Collectors.toList());
    }
}
