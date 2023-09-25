package com.danielcode.customer;

import com.danielcode.exception.ResourceNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT * FROM customer;
                """;

        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql  = """
                SELECT * FROM customer
                WHERE customer.id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES(?,?,?);
                """;
        jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?;
                """;
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public void updateCustomerInfos(Customer customer) {
        var sql = """
                UPDATE customer
                SET name = ?,
                email = ?,
                age = ?
                WHERE
                id = ?;           \s
                """;
        jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge(),
                customer.getId()
        );
    }

    @Override
    public boolean existsCustomerWithId(Integer id) {
        var sql = """
                SELECT * FROM customer
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT * FROM customer
                WHERE email = ?;
                """;
        return jdbcTemplate.query(sql, customerRowMapper, email)
                .stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }
}
