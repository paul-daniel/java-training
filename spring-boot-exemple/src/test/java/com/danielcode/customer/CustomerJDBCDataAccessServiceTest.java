package com.danielcode.customer;

import com.danielcode.AbstractTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainer {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setup(){
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );
        underTest.insertCustomer(customer);

        // WHEN
        List<Customer> customers = underTest.selectAllCustomers();

        // THEN
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // THEN
        assertThat(actual).isPresent().hasValueSatisfying(
                c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getName()).isEqualTo(customer.getName());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                }
        );
    }

    @Test
    void willReturnEmptyWhenSelectCustomerByID() {
        // GIVEN
        int id = -1;

        // WHEN
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // THEN
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );

        underTest.insertCustomer(customer);

        Customer customerInserted = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .findFirst()
                .orElseThrow();

        // WHEN


        // THEN
        assertThat(customerInserted.getEmail()).isEqualTo(customer.getEmail());
        assertThat(customerInserted.getAge()).isEqualTo(customer.getAge());
        assertThat(customerInserted.getName()).isEqualTo(customer.getName());
    }

    @Test
    void deleteCustomerById() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        underTest.deleteCustomerById(id);
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // THEN
        assertThat(actual).isEmpty();
    }

    @Test
    void updateCustomerInfos() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Customer updatedCustomer = new Customer(
                id,
                "jean85" + "-" + UUID.randomUUID(),
                "jean85@gmail.com" + "-" + UUID.randomUUID(),
                20

        );

        // WHEN
        underTest.updateCustomerInfos(updatedCustomer);
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // THEN
        assertThat(actual)
                .isPresent()
                .hasValueSatisfying(
                        c -> {
                            assertThat(c.getId()).isEqualTo(id);
                            assertThat(c.getName()).isEqualTo(updatedCustomer.getName());
                            assertThat(c.getEmail()).isEqualTo(updatedCustomer.getEmail());
                            assertThat(c.getAge()).isEqualTo(updatedCustomer.getAge());
                        }
                );
    }

    @Test
    void existsCustomerWithId() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(customer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // WHEN
        boolean expected = underTest.existsCustomerWithId(id);

        // THEN
        assertThat(expected).isTrue();
    }

    @Test
    void doesNotExistWithId(){
        // WHEN
        boolean expected = underTest.existsCustomerWithId(-1);

        // THEN
        assertThat(expected).isFalse();
    }

    @Test
    void existsPersonWithEmail() {
        // GIVEN
        Customer customer = new Customer(
                FAKER.name().firstName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.number().numberBetween(16,99)
        );
        underTest.insertCustomer(customer);

        String email = underTest.selectAllCustomers()
                .stream()
                .map(Customer::getEmail)
                .filter(cEmail -> cEmail.equals(customer.getEmail()))
                .findFirst()
                .orElseThrow();

        // WHEN
        boolean expected = underTest.existsPersonWithEmail(email);

        // THEN
        assertThat(expected).isTrue();
    }

    @Test
    void doesNotExistPersonWithEmail(){
        // WHEN
        boolean expected = underTest.existsPersonWithEmail("test-1.%s@gmail.com".formatted(UUID.randomUUID()));

        // THEN
        assertThat(expected).isFalse();
    }
}