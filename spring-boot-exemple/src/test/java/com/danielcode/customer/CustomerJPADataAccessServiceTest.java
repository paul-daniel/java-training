package com.danielcode.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService undertest;
    private AutoCloseable autoCloseable;
    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations. openMocks(this);
        undertest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // WHEN
        undertest.selectAllCustomers();

        // THEN
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        // GIVEN
        int  id = 1;

        // WHEN
        undertest.selectCustomerById(id);

        // THEN
        verify(customerRepository).findById(id);
    }

    @Test
    void existsPersonWithEmail() {
        // GIVEN
        String email = "fake@exemple.test";

        // WHEN
        undertest.existsPersonWithEmail(email);

        // THEN
        verify(customerRepository).existsByEmail(email);
    }

    @Test
    void insertCustomer() {
        // GIVEN
        Customer customer = new Customer(
                "test",
                "test@exemple.test",
                22
        );

        // WHEN
        undertest.insertCustomer(customer);

        // THEN
        verify(customerRepository).save(customer);
    }

    @Test
    void deleteCustomerById() {
        // GIVEN
        int id = 1;

        // WHEN
        undertest.deleteCustomerById(id);

        // THEN
        verify(customerRepository).deleteById(id);
    }

    @Test
    void existsCustomerWithId() {
        // GIVEN
        int id = 1;

        // WHEN
        undertest.existsCustomerWithId(id);

        // THEN
        verify(customerRepository).existsById(id);
    }

    @Test
    void updateCustomerInfos() {
        // GIVEN
        Customer customer = new Customer(
                "test",
                "test@exemple.test",
                22
        );

        // WHEN
        undertest.updateCustomerInfos(customer);

        // THEN
        verify(customerRepository).save(customer);
    }
}