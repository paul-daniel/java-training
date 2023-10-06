package com.danielcode.customer;

import com.danielcode.exception.NoChangeDetectedException;
import com.danielcode.exception.ResourceAlreadyExistException;
import com.danielcode.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CustomerServiceTest {

    private CustomerService underTest;
    private AutoCloseable autoCloseable;

    @Mock
    private CustomerDao customerDao;

    @Mock
    private Customer customerMock;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllCustomers() {
        // WHEN
        underTest.getAllCustomers();

        // THEN
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void getCustomerById() {
        // GIVEN
        int id = 1;
        Customer customer = new Customer(
                id,
                "test",
                "test@exemple.test",
                22
        );

        // WHEN
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        Customer actual = underTest.getCustomerById(id);

        // THEN
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowErrorIfCustomerDontExist(){
        // GIVEN
        int id = -1;

        // WHEN
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // THEN
        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(
                        "Customer with id [%s] does not exist".formatted(id)
                );

    }

    @Test
    void throwErrorIfCustomerExistAtInsert() {
        // GIVEN
        String email = "test@exemple.test";

        CustomerRegistrationRequest customerRequest = new CustomerRegistrationRequest(
                "test",
                email,
                22
        );

        // WHEN
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        // THEN
        assertThatThrownBy(() -> underTest.addCustomer(customerRequest))
                .isInstanceOf(ResourceAlreadyExistException.class)
                .hasMessageContaining("Customer with provided email already exist");
    }

    @Test
    void addCustomer(){
        // GIVEN
        String email = "test@exemple.test";

        CustomerRegistrationRequest customerRequest = new CustomerRegistrationRequest(
                "test",
                email,
                22
        );

        // WHEN
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        underTest.addCustomer(customerRequest);

        // THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getAge()).isEqualTo(customerRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customerRequest.email());
        assertThat(capturedCustomer.getName()).isEqualTo(customerRequest.name());
    }

    @Test
    void throwErrorIfCustomerIdDontExist(){
        // GIVEN
        int id = -1;

        // THEN
        when(customerDao.existsCustomerWithId(id)).thenReturn(false);

        //THEN
        assertThatThrownBy(()-> underTest.removeCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer with id [%s] does not exist".formatted(id));
    }

    @Test
    void removeCustomer() {
        // GIVEN
        int id = 1;

        // WHEN
        when(customerDao.existsCustomerWithId(id)).thenReturn(true);
        underTest.removeCustomer(id);

        // THEN
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void updateCustomer() {
        // GIVEN
        int id = 1;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "test",
                "test@test.gmail",
                23
        );

        // WHEN
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customerMock));
        when(customerDao.existsCustomerWithId(id)).thenReturn(true);
        when(customerDao.existsPersonWithEmail(request.email())).thenReturn(false);

        when(customerMock.getEmail()).thenReturn("test@exemple.test");

        when(customerMock.getName()).thenReturn("test3");

        when(customerMock.getAge()).thenReturn(22);

        underTest.updateCustomer(id, request);

        // THEN
        verify(customerMock).setEmail("test@test.gmail");
        verify(customerMock).setAge(23);
        verify(customerDao).updateCustomerInfos(customerMock);
    }

    @Test
    void updateThrowExceptionIfUpdatedMailIsAlreadyTaken(){
        //GIVEN
        int id = 1;
        String email = "test@test.gmail";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "test",
                email,
                23
        );

        //WHEN
        when(customerDao.existsCustomerWithId(id)).thenReturn(true);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customerMock));

        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        when(customerMock.getEmail()).thenReturn("test@exemple.test");

        when(customerMock.getName()).thenReturn("test3");

        when(customerMock.getAge()).thenReturn(22);

        //THEN

        assertThatThrownBy( () -> underTest.updateCustomer(id,request))
                .isInstanceOf(ResourceAlreadyExistException.class)
                .hasMessageContaining("email already taken");
    }

    @Test
    void shouldDetectNoChangeOnUpdate(){
        //GIVEN
        int id = 1;
        String email = "test@test.gmail";
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "test",
                email,
                23
        );

        //WHEN
        when(customerDao.existsCustomerWithId(id)).thenReturn(true);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customerMock));

        when(customerMock.getEmail()).thenReturn(email);

        when(customerMock.getName()).thenReturn("test");

        when(customerMock.getAge()).thenReturn(23);

        //THEN

        assertThatThrownBy( () -> underTest.updateCustomer(id,request))
                .isInstanceOf(NoChangeDetectedException.class)
                .hasMessageContaining("No change detected");
    }

    @Test
    void shouldThrowExceptionIfCustomerDontExist(){
        //GIVEN
        int id = 1;
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "test",
                "test@test.gmail",
                23
        );

        //WHEN
        when(customerDao.existsCustomerWithId(id)).thenReturn(false);
        //THEN

        assertThatThrownBy( () -> underTest.updateCustomer(id,request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer with id [%s] does not exist".formatted(id));
    }
}