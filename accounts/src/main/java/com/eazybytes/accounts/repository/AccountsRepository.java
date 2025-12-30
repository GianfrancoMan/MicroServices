package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    /**
     *
     * @param customerId - Customer id of the customer
     * @return - Accounts object
     */
    Optional<Accounts> findByCustomerId(Long customerId);

    /**
     *
     * @param customerId - Customer id of the customer
     */
    @Transactional
    @Modifying //Quando un metodo personalizzato modifica il DB si devono usare queste due annotazioni @Modifyng indica a Spring che il metodo modifica il DB, @Transactional indica a Spring che la transazione deve essere completata altrimenti ci sarà una roolback
    void deleteByCustomerId(Long customerId);
}
