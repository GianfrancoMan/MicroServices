package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Creo un metodo tramimte jpa method naming convention per trovare un cliente
    //tramite il numero di telefono
    Optional<Customer> findByMobileNumber(String mobileNumber);
}
