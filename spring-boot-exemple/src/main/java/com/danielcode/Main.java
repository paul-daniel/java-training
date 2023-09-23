package com.danielcode;

import com.danielcode.customer.Customer;
import com.danielcode.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            Customer alex = new Customer("Alex", "alex@gmail.com", 21);
            Customer claude = new Customer("Claude", "claude@gmail.com", 25);
            List<Customer> customers = List.of(alex, claude);
            customerRepository.saveAll(customers);
        };
    }
}
