package com.amigoscode.spring_boot_project.customer;

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
                SELECT id, name, email, age
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, customerId)
                .stream()
                .findFirst();

    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer (name, email, age)
                VALUES (?, ?, ?)
                """;
        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        System.out.println(" jdbcTemplate.update = " + result);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM customer
                    WHERE email = ?
                )
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, email);
    }

    @Override
    public boolean existsPersonWithId(Integer customerId) {
        var sql = """
                SELECT EXISTS(
                    SELECT 1
                    FROM customer
                    WHERE id = ?
                )
                """;
        return jdbcTemplate.queryForObject(sql, Boolean.class, customerId);
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, customerId);
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) {
        var sql = """
                UPDATE customer
                SET name = ?,
                    email = ?,
                    age = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(
                sql,
                updatedCustomer.getName(),
                updatedCustomer.getEmail(),
                updatedCustomer.getAge(),
                updatedCustomer.getId()
        );
    }
}
