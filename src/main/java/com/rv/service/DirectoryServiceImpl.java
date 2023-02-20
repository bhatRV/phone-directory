package com.rv.service;


import com.rv.entities.Customer;
import com.rv.entities.PhoneNumber;
import com.rv.entities.Status;
import com.rv.exception.DuplicateDataException;
import com.rv.exception.InvalidInputException;
import com.rv.exception.NoRecordsFoundException;
import com.rv.model.CustomerData;
import com.rv.model.PhoneNumberData;
import com.rv.repository.CustomerRepository;
import com.rv.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.rv.config.Constants.ERROR_MSG_DUPLICTE_RECORD;
import static com.rv.config.Constants.ERROR_MSG_INVALID_INPUT;
import static com.rv.config.Constants.ERROR_MSG_INVALID_PHONE;
import static com.rv.config.Constants.ERROR_MSG_NO_CUSTOMER_RECORDS_FOUND;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Override

    public void addCustomer(List<CustomerData> custData) {
        custData.forEach(cust -> {
                    Customer customer = Customer.builder()
                            .firstName(cust.getFirstName())
                            .lastName(cust.getLastName())
                            .phoneNumbers(cust.getPhoneNumbers().stream()
                                    .map(x -> PhoneNumber.builder().number(x.getNumber())
                                            .status(Status.NEW).build())
                                    .collect(Collectors.toSet()))
                            .build();

                     try {
                        customerRepository.save(customer);
                    } catch (DataIntegrityViolationException ex) {
                        throw new DuplicateDataException(ERROR_MSG_DUPLICTE_RECORD);
                    }
                    catch (Exception ex) {
                        throw new InvalidInputException(ERROR_MSG_INVALID_INPUT);
                    }
                }

        );

    }

    public List<PhoneNumberData> getAllPhoneNumbersForCustomers(final Long customerId) {
        return customerRepository.findById(customerId)
                .map(c -> c.getPhoneNumbers().stream()
                        .map(x -> PhoneNumberData.builder().number(x.getNumber()).status(x.getStatus().name()).build())
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new NoRecordsFoundException(ERROR_MSG_NO_CUSTOMER_RECORDS_FOUND));
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneRepository.findAll();
    }

    @Override
    public void activatePhoneNumber(String pNumber) {
        Optional<PhoneNumber> phoneNumber = phoneRepository.findByNumber(pNumber);

           if (phoneNumber.isPresent()) {
            phoneNumber.get().setStatus(Status.ACTIVE);
            phoneRepository.save(phoneNumber.get());
        } else {
            throw new InvalidInputException(ERROR_MSG_INVALID_PHONE);
        }
    }

    @Override
    public List<PhoneNumber> getAllPhoneNumbersPagination() {
        return phoneRepository.findAll();
    }


}