package com.danielcode;

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
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args);
        System.out.println(Arrays.toString(ctx.getBeanDefinitionNames()));
    }

    @Bean("foo")
    public Foo getFoo(){
        return new Foo("bar");
    }

    public record Foo(String name){}
}
