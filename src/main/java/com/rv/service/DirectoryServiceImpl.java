package com.rv.service;


import com.rv.entities.Customer;
import com.rv.entities.PhoneNumber;
import com.rv.entities.Status;
import com.rv.exception.InvalidInputException;
import com.rv.exception.NoRecordsFoundException;
import com.rv.repository.CustomerRepository;
import com.rv.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.rv.config.Constants.ERROR_MSG_INVALID_PHONE;
import static com.rv.config.Constants.ERROR_MSG_NO_CUSTOMER_RECORDS_FOUND;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Override

    public Long addCustomer(Customer customer) {
        return customerRepository.save(customer).getId();
    }


    @Override
    public Customer getCustomer(Long id) {
        return null;
    }

    public Set<PhoneNumber> getAllPhoneNumbersForCustomers(final Long customerId){
        return customerRepository.findById(customerId)
                .map(c -> c.getPhoneNumbers())
                .orElseThrow(() -> new NoRecordsFoundException(ERROR_MSG_NO_CUSTOMER_RECORDS_FOUND));
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneRepository.findAll();
    }

    @Override
    public void activatePhoneNumber(Long phoneNumberId) {
        PhoneNumber phoneNumber = phoneRepository
                                            .findById(phoneNumberId)
                                            .orElseThrow(() -> new InvalidInputException(ERROR_MSG_INVALID_PHONE));

        phoneNumber.setStatus(Status.ACTIVE);
        phoneRepository.save(phoneNumber);
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbersPagination() {
        return phoneRepository.findAll();
    }
}