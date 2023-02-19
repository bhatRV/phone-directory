package com.rv.service;

import com.rv.entities.Customer;
import com.rv.entities.PhoneNumber;

import java.util.List;
import java.util.Set;

public interface DirectoryService {

    Customer getCustomer(final Long id);

    Set<PhoneNumber> getAllPhoneNumbersForCustomers(final Long customerId);

    List<PhoneNumber> getAllPhoneNumbers();

    void activatePhoneNumber(final Long phoneNumberId);

    Long addCustomer(Customer customer);

    List<PhoneNumber> getAllPhoneNumbersPagination();

}