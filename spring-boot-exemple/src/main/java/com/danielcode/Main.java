package com.danielcode;

import com.danielcode.customer.Customer;
import com.danielcode.customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@SpringBootApplication
@RestController
public class Main {

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
//        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
//        System.out.println(Arrays.toString(ctx.getBeanDefinitionNames()));
    }

    public List<Customer> generateRandomCustomers(int number){
        Faker faker = new Faker();
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < number ; i++){
            String firstname = faker.name().firstName();
            String lastname = faker.name().lastName();
            customers.add(
                    new Customer(
                            "%s %s".formatted(firstname, lastname),
                            "%s.%s@gmail.com".formatted(firstname.toLowerCase(), lastname.toLowerCase()),
                            faker.number().numberBetween(9, 99)
                    )
            );
        }
        return customers;
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            customerRepository.saveAll(generateRandomCustomers(2));
        };
    }
}
