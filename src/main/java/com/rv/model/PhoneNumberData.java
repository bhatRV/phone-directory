package com.rv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneNumberData {

    private String number;
    private String status;

    public PhoneNumberData(String number) {
        this.number = number;
    }
}