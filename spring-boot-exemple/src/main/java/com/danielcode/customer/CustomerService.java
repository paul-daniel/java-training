package com.danielcode.customer;

import com.danielcode.exception.ResourceAlreadyExistException;
import com.danielcode.exception.ResourceNotFoundException;
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

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Integer id){
        Optional<Customer> customer = customerDao.selectCustomerById(id);
        return customer.orElseThrow(() -> new ResourceNotFoundException("Customer with id [%s] does not exist".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        // check if email exist
        if(customerDao.existsPersonWithEmail(customerRegistrationRequest.email())){
            throw new ResourceAlreadyExistException("Customer with provided email already exist");
        }
        customerDao.insertCustomer(new Customer(customerRegistrationRequest));
    }

    public void removeCustomer(Integer id){
        if(!customerDao.existsCustomerWithId(id)){
            throw new ResourceNotFoundException("Customer with id [%s] does not exist".formatted(id));
        }
        customerDao.deleteCustomerById(id);
    }

    public void updateCustomer(Integer id, CustomerRegistrationRequest customerRegistrationRequest){
        if(!customerDao.existsCustomerWithId(id)){
            throw new ResourceNotFoundException("Customer with id [%s] does not exist".formatted(id));
        }
        Customer customer = new Customer(id, customerRegistrationRequest);
        customerDao.updateCustomerInfos(customer);
    }
}
