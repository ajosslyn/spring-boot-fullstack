package com.amigoscode.spring_boot_project.customer;

import com.amigoscode.spring_boot_project.exception.RequestValidationException;
import com.amigoscode.spring_boot_project.exception.DuplicateResourceException;
import com.amigoscode.spring_boot_project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;


    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {

        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        //check if email is taken
        String email = customerRegistrationRequest.email();
        if (customerDao.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException(
                    "email [%s] is taken".formatted(email)
            );
        }
        //add customer
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomer(Integer customerId) {
        //check if customer exists
        if (!customerDao.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }
        customerDao.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest) {
        Customer existingCustomer = getCustomer(customerId);

        boolean isUpdated = false;

        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(existingCustomer.getName())) {
            existingCustomer.setName(customerUpdateRequest.name());
            isUpdated = true;
        }

        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(existingCustomer.getEmail())) {
            if (customerDao.existsPersonWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException(
                        "email [%s] is taken".formatted(customerUpdateRequest.email())
                );
            }
            existingCustomer.setEmail(customerUpdateRequest.email());
            isUpdated = true;
        }

        if (customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(existingCustomer.getAge())) {
            existingCustomer.setAge(customerUpdateRequest.age());
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new RequestValidationException("No information change");
        }

        customerDao.updateCustomer(existingCustomer);
    }
}
