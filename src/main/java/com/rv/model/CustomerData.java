package com.rv.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@ToString
@Builder
public class CustomerData {

    Long customerGeneratedId;

    private String firstName;

    private String lastName;

    private List<PhoneNumberData> phoneNumbers;

}