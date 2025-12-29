package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     *
     * @param customerDto - CoustumerDto object
     */
    void createAccount(CustomerDto customerDto);
}
