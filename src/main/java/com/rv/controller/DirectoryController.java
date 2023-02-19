package com.rv.controller;

import com.rv.entities.Customer;
import com.rv.entities.PhoneNumber;
import com.rv.service.DirectoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/phone-directory")
@Api(value = "Phone Directory Controller" )
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    /** Get all phone numbers of a single customer
     * @return List of phoneNumbers.
     */
    @GetMapping(value = "/customer/{customer_id}", produces = {MediaType.APPLICATION_JSON_VALUE}) public ResponseEntity<?> getCustomerPhoneNumbers(final @PathVariable long customer_id) {
        return ResponseEntity.ok(directoryService.getAllPhoneNumbersForCustomers(customer_id));
    }

    /**
     * Get all phone numbers with the status
     * @return List of Phone numbers
     */

    @ApiOperation(value = "Get All the Phone numbers with the actovation status.",
            notes = "The successful invocation of this request will give you the List of  Phone numbers with Activation Status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned upon successfully execution")

    })
    @GetMapping(value = "/phone_numbers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers() {

        return ResponseEntity.ok(directoryService.getAllPhoneNumbers());
    }


    /**
     * API to add the contacts in to a phone book
     * @param customer
     * @return
     */

    @ApiOperation(value = "Adds contacts to the Phone Directory.",
            notes = "The successful invocation of this request will store the Phone number for a given customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returned upon successfully storing the contact")

    })
    @PostMapping("/customer")

    public ResponseEntity<Long> addCustomerToPhoneBook(@Valid @RequestBody Customer customer) {
        return ResponseEntity.ok(directoryService.addCustomer(customer));
    }

}