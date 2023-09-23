package com.danielcode.customer;

import com.danielcode.exception.NoChangeDetectedException;
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

    public void updateCustomer(Integer id, CustomerRegistrationRequest request){
        // Make changes only when the need to is detected
        if(!customerDao.existsCustomerWithId(id)){
            throw new ResourceNotFoundException("Customer with id [%s] does not exist".formatted(id));
        }

        Customer customer = this.getCustomerById(id);
        boolean changes = false;

        if(!customer.getEmail().equals(request.email())){
            if(customerDao.existsPersonWithEmail(request.email())){
                throw new ResourceAlreadyExistException("email already taken");
            }else{
                customer.setEmail(request.email());
                changes = true;
            }

        }

        if(!customer.getName().equals(request.name())){
            customer.setName(request.name());
            changes = true;
        }

        if(!customer.getAge().equals(request.age())){
            customer.setAge(request.age());
            changes = true;
        }

        if(changes){
            customerDao.updateCustomerInfos(customer);
        }else{
            throw new NoChangeDetectedException("No change detected");
        }
    }
}
