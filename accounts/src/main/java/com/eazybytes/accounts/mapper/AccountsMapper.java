package com.eazybytes.accounts.mapper;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.entity.Accounts;

public class AccountsMapper {
    //Prende in input due oggetti Accounts e AccountDto
    //Mappa e restituisce l'oggetto AccountsDto con i valori dell'Accounts
    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accountsDto;
    }

    //Prende in input due oggetti AccountsDto e Accounts
    //Mappa e restituisce L'oogetto Accounts con i valori dell'oggetto AccountsDto
    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return  accounts;
    }
}
