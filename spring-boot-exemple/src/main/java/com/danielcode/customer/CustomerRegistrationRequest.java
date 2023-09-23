package com.danielcode.customer;

public record CustomerRegistrationRequest(String name,
                                          String email,
                                          Integer age){
}