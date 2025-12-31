package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass //used as base clas for other entities.
@EntityListeners(AuditingEntityListener.class)//relativo all'auditing per il popolamento automatico dei metadati
@Getter @Setter @ToString
public class BaseEntity {

    /*@CreateDate, @CreateBy, @LastModifiedDate e LastModifiedBy
    * Sono annotazioni che abilitano JPA a popolare questi campi automaticamente
    * Ogni volta che c'è un inserzione o una modifica nel del DB.
    * Tuttavia  JPA non sa ne quando ne chi esegue queste operazioni, per questo
    * è neccessario implementare la logica neccessaria(vedi audit package)
    * inoltre per abilitare l'auditing JPA, e nessario usare l'annotazione @EnableJpaAuditing
    * nella classe che contiene il main, AccountsApplication*/
    @CreatedDate
    @Column(updatable = false)// if anyone tries to update this field, it will not allow.
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private  String createdBy;

    @LastModifiedDate
    @Column(insertable = false)//prevent that jpa populate this field when it is created, because it's about to update.
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;
}
