package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     *
     * @param customerDto - CoustumerDto object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Mobile number of the customer
     * @return - CustomerDto object
     */
    CustomerDto fetchAccountDetails(String mobileNumber);

    /**
     *The requirement is to update the account details of a customer, using CustomerDto object
     * but isn't allowed to update the account number that is present in the accountDto property
     * of the CustomerDto object.
     *
     * @param customerDto - CoustumerDto object
     * @return - boolean
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Mobile number of the customer
     * @return - boolean
     */
    boolean deleteAccount(String mobileNumber);
}
