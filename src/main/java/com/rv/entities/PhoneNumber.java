package com.rv.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {

/*
   @GeneratedValue
    private Long id;*/

    @Id
    private String number;

    @Enumerated(EnumType.STRING)
    private Status status;
}