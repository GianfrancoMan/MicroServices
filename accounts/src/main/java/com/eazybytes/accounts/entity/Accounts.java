package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table(name = "accounts")  //when the class's name is different from the table's name, we can use this annotation to specify the table's name.
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Accounts extends BaseEntity {
    @Column(name = "customer_id")
    private Long customerId;
    @Id
    @Column(name = "account_number")//This field will not be generated automatically by JPA, there will be an internal logic that will create the account number
    private Long accountNumber;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "branch_address")
    private String branchAddress;
    /*Actually, spring jpa is smart enough to guess that customerId corresponds to customer_id,
    but I keep this annotation to remember what to do if the field name and the column name in the table do not match.*/
}
