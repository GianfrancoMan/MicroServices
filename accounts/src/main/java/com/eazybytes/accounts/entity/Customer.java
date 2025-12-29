package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table(name = "customers")  //when the class's name is different from the table's name, we can use this annotation to specify the table's name.
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")//as for the table name, if the column name is different from the field name, we can use this annotation to specify the column's name.
    private Long customerId;
    private String name;
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;
    //Actually, spring jpa is smart enough to guess that customerId corresponds to customer_id,
    // but I keep this annotation to remember what to do if the field name and the column name in the table do not match.
}
