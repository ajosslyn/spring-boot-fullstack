package com.amigoscode.spring_boot_project.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    // DB
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(
                1,
                "Alex",
                "alex@email",
                25
        );
        customers.add(alex);

        Customer jamila = new Customer(
                2,
                "Jamila",
                "jamila@email.com",
                20
        );
        customers.add(jamila);
    }


    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        Optional<Customer> customer = customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst();
        return customer;
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonWithId(Integer customerId) {
        return customers.stream()
                .anyMatch(c -> c.getId().equals(customerId));
    }

    @Override
    public void deleteCustomerById(Integer customerId) {
       customers.removeIf(c -> c.getId().equals(customerId));
    }

    @Override
    public void updateCustomer(Customer customer) {
        customers.stream()
                .filter(c -> c.getId().equals(customer.getId()))
                .findFirst()
                .ifPresent(c -> {
                    c.setName(customer.getName());
                    c.setEmail(customer.getEmail());
                    c.setAge(customer.getAge());
                });
    }
}
