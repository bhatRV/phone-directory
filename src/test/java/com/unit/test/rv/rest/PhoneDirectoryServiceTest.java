package com.unit.test.rv.rest;


import com.rv.PhoneDirectoryApplication;

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
import com.rv.service.DirectoryService;
import com.rv.service.DirectoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PhoneDirectoryApplication.class)
@ActiveProfiles("test")
public class PhoneDirectoryServiceTest {

    @InjectMocks
    private DirectoryServiceImpl phoneDirectoryService;
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Autowired
    private WebTestClient webTestClient;


    @BeforeEach
    public void init() {

    }

   @Test
    public void shouldAddCustomerDataSuccessfully() {
        //Mock
        when(customerRepository.save(any())).thenReturn(new Customer());
        //Input data
         List<CustomerData> custList = new ArrayList<>();
         custList.add(getCustomerData("Rahul","Dravid"));
         custList.add(getCustomerData("XXXX","YYYY"));
         custList.add(getCustomerData("AAAA","BBB"));

        // Actual result
        phoneDirectoryService.addCustomer(custList);

        // Assertion
       verify(customerRepository,times(3)).save(any());
    }

    @Test
    public void shouldErrorWhenCustomerDataNotSaved() {
        //Mock
        when(customerRepository.save(any())).thenThrow(DataIntegrityViolationException.class);
        //Input data
        List<CustomerData> custList = new ArrayList<>();
        custList.add(getCustomerData("Rahul","Dravid"));
        custList.add(getCustomerData("XXXX","YYYY"));
        custList.add(getCustomerData("AAAA","BBB"));

        // Actual result
        assertThrows(DuplicateDataException.class,() -> phoneDirectoryService.addCustomer(custList));

        // Assertion
        verify(customerRepository,times(1)).save(any());
    }


    @Test
    public void shouldThrowInvalidInputWhenCustomerDataNotSaved() {
        //Mock
        when(customerRepository.save(any())).thenThrow(InvalidInputException.class);
        //Input data
        List<CustomerData> custList = new ArrayList<>();
        custList.add(getCustomerData("Rahul","Dravid"));
        custList.add(getCustomerData("XXXX","YYYY"));
        custList.add(getCustomerData("AAAA","BBB"));

        // Actual result
        assertThrows(InvalidInputException.class,() -> phoneDirectoryService.addCustomer(custList));

        // Assertion
        verify(customerRepository,times(1)).save(any());
    }


    @Test
    public void shouldGetAllPhoneNumbersForCustomersSuccessfully() {
        //Mock
        Set<PhoneNumber> phNumber=new HashSet<>();
        phNumber.add(PhoneNumber.builder().number("123213213123").status(Status.ACTIVE).build());
        phNumber.add(PhoneNumber.builder().number("1231313123123").status(Status.ACTIVE).build());

        Customer cust=Customer.builder()
                .firstName("RV")
                .lastName("Bhat")
                .phoneNumbers(phNumber)
                .build();
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(cust));


        // Actual result
       List<PhoneNumberData> ph = phoneDirectoryService.getAllPhoneNumbersForCustomers(1L);

        // Assertion

        assertEquals(2, ph.size());
        assertEquals(Status.ACTIVE.name(), ph.get(0).getStatus());


    }


    @Test
    public void shouldThrowErrorWhenNoPhoneNumbersForCustomersFound() {
        //Mock

        when(customerRepository.findById(any())).thenThrow(NoRecordsFoundException.class);

        // Actual result & assert throws
        assertThrows(NoRecordsFoundException.class,() -> phoneDirectoryService.getAllPhoneNumbersForCustomers(1L));
 }


    @Test
    public void shouldGetAllPhoneNumbersSuccessfully() {
        //Mock
        List<PhoneNumber> phNumber=new ArrayList<>();
        phNumber.add(PhoneNumber.builder().number("123213213123").status(Status.ACTIVE).build());
        phNumber.add(PhoneNumber.builder().number("1231313123123").status(Status.INACTIVE).build());


        when(phoneRepository.findAll()).thenReturn(phNumber);


        // Actual result
        List<PhoneNumber> ph = phoneDirectoryService.getAllPhoneNumbers();

        // Assertion

        assertEquals(2, ph.size());
        assertEquals("123213213123", ph.get(0).getNumber());
        assertEquals(Status.ACTIVE, ph.get(0).getStatus());

        assertEquals("1231313123123", ph.get(1).getNumber());
        assertEquals(Status.INACTIVE, ph.get(1).getStatus());


    }


    @Test
    public void shouldThrowErrorWhenNoPhoneNumbersFound() {
        //Mock

        when(phoneRepository.findAll()).thenThrow(NoRecordsFoundException.class);

        // Actual result & assert throws
        assertThrows(NoRecordsFoundException.class,() -> phoneDirectoryService.getAllPhoneNumbers());
    }



    @Test
    public void shouldActivateSuccessfully() {
            when(phoneRepository.findByNumber(any())).thenReturn(Optional.of(PhoneNumber.builder().number("123213213123").status(Status.ACTIVE).build()));


        // Actual result
         phoneDirectoryService.activatePhoneNumber("123213213123");

        // Assertion

        verify(phoneRepository,times(1)).findByNumber(any());



    }


    @Test
    public void shouldThrowExceptionWhenActivationFails() {
        when(phoneRepository.findByNumber(any())).thenReturn(Optional.empty());

        // Actual result
        assertThrows(InvalidInputException.class,() -> phoneDirectoryService.activatePhoneNumber("123213213123"));

        // Assertion

        verify(phoneRepository,times(1)).findByNumber(any());
    }



    private CustomerData getCustomerData(String firstName, String lastName) {
        List<PhoneNumberData> list= new ArrayList<>();
        list.add(PhoneNumberData.builder().number(genRandomPhoneNum()).build());
        list.add(PhoneNumberData.builder().number(genRandomPhoneNum()).build());
        list.add(PhoneNumberData.builder().number(genRandomPhoneNum()).build());

        return CustomerData.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumbers(list).build();
     }

    public String genRandomPhoneNum() {
        Random r = new Random(System.currentTimeMillis());
        int x= 1000000000 + r.nextInt(2000000000);
        return String.valueOf(x);
    }


}




