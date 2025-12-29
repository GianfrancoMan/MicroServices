package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass //used as base clas for other entities.
@Getter @Setter @ToString
public class BaseEntity {

    @Column(updatable = false)// if anyone tries to update this field, it will not allow.
    private LocalDateTime createdAt;
    @Column(updatable = false)
    private  String createdBy;
    @Column(insertable = false)//prevent that jpa populate this field when it is created, because it's about to update.
    private LocalDateTime updatedAt;
    @Column(insertable = false)
    private String updatedBy;
}
