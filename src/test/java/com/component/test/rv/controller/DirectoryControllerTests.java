package com.component.test.rv.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rv.controller.DirectoryController;
import com.rv.entities.PhoneNumber;
import com.rv.entities.Status;
import com.rv.model.CustomerData;
import com.rv.model.PhoneNumberData;
import com.rv.service.DirectoryService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DirectoryController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class DirectoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectoryService directoryService;

    private List<CustomerData> customerList;

    @BeforeAll
    public void setupTest() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PhoneNumberData> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumberData( "0400111222", "INACTIVE"));
        phoneNumbers.add(new PhoneNumberData("0391112222", "ACTIVE"));

        List<PhoneNumberData> phoneNumbers1 = new ArrayList<>();
        phoneNumbers1.add(new PhoneNumberData( "1400111222", "INACTIVE"));
        phoneNumbers1.add(new PhoneNumberData("1391112222", "ACTIVE"));

        customerList = new ArrayList<>();
        customerList.add( CustomerData.builder().lastName("XXX").firstName("YYY").phoneNumbers(phoneNumbers).build());
        customerList.add( CustomerData.builder().lastName("AAA").firstName("BBB").phoneNumbers(phoneNumbers1).build());
    }

    @Test
    public void getCustomerPhoneNumbers_shouldReturnCustomerPhoneNumbers() throws Exception {
        // Given
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber( 0001L,"0400111222", Status.INACTIVE,0001L));
        phoneNumbers.add(new PhoneNumber(0002L,"0391112222", Status.ACTIVE,0002L));


        // When
        when(directoryService.getAllPhoneNumbers()).thenReturn(phoneNumbers);

        // Then
        mockMvc.perform(get("/v1/customers/1001"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}