package com.component.test.rv.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rv.entities.PhoneNumber;
import com.rv.entities.Status;
import com.rv.service.DirectoryService;
import com.rv.service.DirectoryServiceImpl;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
public class ActivationControllerTests {

    @Autowired
    private MockMvc mockMvc;


    @InjectMocks
    private DirectoryServiceImpl directoryService;

    @Test
    public void getAllPhoneNumbers_shouldReturnListOfPhoneNumbers() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber(1001L, "0400111222", Status.INACTIVE, 001L));
        phoneNumbers.add(new PhoneNumber(1002L, "0391112222", Status.ACTIVE,002L));

        when(directoryService.getAllPhoneNumbers()).thenReturn(phoneNumbers);

        MvcResult result = mockMvc.perform(get("/v1/phone_numbers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(phoneNumbers);
        assertEquals(expectedResponse, actualResponseBody);
    }



}