package com.danielcode.journeys;

import com.danielcode.customer.Customer;
import com.danielcode.customer.CustomerRegistrationRequest;
import com.danielcode.exception.ResourceNotFoundException;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final String customerURI = "/api/v1/customers";

    @Test
    void customerRegistrationRequest(){
        // Register customer
        Faker faker = new Faker();
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
            faker.name().fullName()+"daliali",
            faker.internet().safeEmailAddress(),
            faker.number().numberBetween(12,99)
        );
        webTestClient.post().uri(customerURI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus()
                .isOk();

        // Get all customers
        List<Customer> allCustomers = webTestClient.get().uri(customerURI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        // Make sure the customer is present
        Customer expectedCustomer = new Customer(request);

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        // Get Customer by id

        assert allCustomers != null;
        var id = allCustomers.stream()
                        .filter(c -> c.getEmail().equals(expectedCustomer.getEmail()))
                        .map(Customer::getId)
                        .findFirst()
                        .orElseThrow(() ->
                                new ResourceNotFoundException("No id with [%s] email found".formatted(expectedCustomer.getEmail())
                                )
                        );

        expectedCustomer.setId(id);

        webTestClient.get()
                .uri(customerURI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {})
                .isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteCustomer(){
        // Register customer
        Faker faker = new Faker();
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                faker.name().fullName()+"daliali",
                faker.internet().safeEmailAddress(),
                faker.number().numberBetween(12,99)
        );
        webTestClient.post().uri(customerURI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus()
                .isOk();

        // Get all customers
        List<Customer> allCustomers = webTestClient.get().uri(customerURI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        Customer expectedCustomer = new Customer(request);

        // Get Customer id
        assert allCustomers != null;
        var id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(expectedCustomer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("No id with [%s] email found".formatted(expectedCustomer.getEmail())
                        )
                );

        expectedCustomer.setId(id);

        // Delete Customer by id

        webTestClient.delete()
                .uri(customerURI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


        webTestClient.get()
                .uri(customerURI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdate(){
        // Register customer
        Faker faker = new Faker();
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                faker.name().fullName()+"daliali",
                faker.internet().safeEmailAddress(),
                faker.number().numberBetween(12,99)
        );
        webTestClient.post().uri(customerURI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(request))
                .exchange()
                .expectStatus()
                .isOk();

        // Get all customers
        List<Customer> allCustomers = webTestClient.get().uri(customerURI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        // Make sure the customer is present
        Customer createdCustomer = new Customer(request);

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(createdCustomer);

        // Get Customer id

        assert allCustomers != null;
        var id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(createdCustomer.getEmail()))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("No id with [%s] email found".formatted(createdCustomer.getEmail())
                        )
                );

        createdCustomer.setId(id);

        // Create updated customer request
        CustomerRegistrationRequest updateRequest = new CustomerRegistrationRequest(
                faker.name().fullName()+"daliali",
                faker.internet().safeEmailAddress(),
                faker.number().numberBetween(12,99)
        );

        Customer updatedCustomer = new Customer(updateRequest);
        updatedCustomer.setId(id);

        webTestClient.put()
                .uri(customerURI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(updateRequest))
                .exchange()
                .expectStatus()
                .isOk();

        // Verify the customer was updated
        webTestClient.get()
                .uri(customerURI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {})
                .isEqualTo(updatedCustomer);
    }
}
