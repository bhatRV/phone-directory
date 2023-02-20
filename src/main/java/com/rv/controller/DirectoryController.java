package com.rv.controller;

import com.rv.entities.PhoneNumber;
import com.rv.model.CustomerData;
import com.rv.model.PhoneNumberData;
import com.rv.service.DirectoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/phone-directory")
public class DirectoryController {
    @Autowired
    private DirectoryService directoryService;

    /**
     * Get all phone numbers of a single customer
     *
     * @return List of phoneNumbers.
     */
    @Operation(summary = "Get all customer phone Numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of phone numbers", content =
            @Content(array = @ArraySchema(schema = @Schema(implementation = PhoneNumberData.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content)})
    @GetMapping(value = "/customer/{customer_id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PhoneNumberData>> getCustomerPhoneNumbers(final @PathVariable Long customer_id) {
        return ResponseEntity.ok(directoryService.getAllPhoneNumbersForCustomers(customer_id));
    }

    /**
     * Get all phone numbers with the status
     *
     * @return List of Phone numbers
     */
    @GetMapping(value = "/phone_numbers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers() {

        return ResponseEntity.ok(directoryService.getAllPhoneNumbers());
    }


    /**
     * API to add the contacts in to a phone book
     *
     * @return
     */


    @PostMapping("/customers")
    public HttpStatus addCustomerWithPhoneNumbers(@RequestBody List<CustomerData> custData) {
        System.out.println(" Incoming Customer: " + custData);
        directoryService.addCustomer(custData);
        return HttpStatus.OK;

    }


}