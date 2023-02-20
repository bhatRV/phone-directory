package com.rv.service;

import com.rv.entities.Customer;
import com.rv.entities.PhoneNumber;
import com.rv.model.CustomerData;
import com.rv.model.PhoneNumberData;

import java.util.List;

public interface DirectoryService {

    List<PhoneNumberData> getAllPhoneNumbersForCustomers(final Long customerId);

    List<PhoneNumber> getAllPhoneNumbers();

    void activatePhoneNumber(final String phoneNumber);

    void addCustomer(List<CustomerData> customerData);

    List<PhoneNumber> getAllPhoneNumbersPagination();

}