package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.ICustomerService;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient; //per interrogare il repository del microservizio CARDS dobbiamo iniettare il bean CardsFeignClient
    private final LoansFeignClient loansFeignClient; //per interrogare il repository del microservizio LOANS dobbiamo iniettare il bean LoansFeignClient

    /**
     *
     * @param mobileNumber
     * @return  CustomerDetailsDto with whole customer's details based to its mobile number
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->  new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->  new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        customerDetailsDto.setLoansDto(loansFeignClient.fetchLoanDetails(mobileNumber).getBody());
        customerDetailsDto.setCardsDto(cardsFeignClient.fetchCardDetails(mobileNumber).getBody());

        return customerDetailsDto;

    }
}
