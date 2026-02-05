package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Customer;

public class CustomerMapper {

    //Prende in input due oggetti Customer e CustomerDto
    //Mappa e restituisce l'oggetto customerDto con i valori dell'Customer
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    //Prende in input due oggetti CustomerDto e Customer
    //Mappa e restituisce L'oogetto Customer con i valori dell'oggetto CustomerDto
    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    //Prende in input due oggetti Customer e CustomerDetailsDto
    //Mappa e restituisce l'oggetto CustomerDetailsDto con i valori dell'Customer
    public static CustomerDetailsDto mapToCustomerDetailsDto(Customer customer, CustomerDetailsDto customerDetailsDto) {
        customerDetailsDto.setEmail(customer.getEmail());
        customerDetailsDto.setName(customer.getName());
        customerDetailsDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailsDto;
    }
}
