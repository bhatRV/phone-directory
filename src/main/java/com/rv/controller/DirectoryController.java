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

    @Operation(summary = "Get all phone numbers of a given customer based on the customer Id Provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of phone numbers", content =
            @Content(array = @ArraySchema(schema = @Schema(implementation = PhoneNumberData.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entry/Data not found",
                    content = @Content)})

    @GetMapping(value = "/customer/{customer_id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PhoneNumberData>> getCustomerPhoneNumbers(final @PathVariable Long customer_id) {
        return ResponseEntity.ok(directoryService.getAllPhoneNumbersForCustomers(customer_id));
    }


    @Operation(summary = "Get all phone numbers across all customers with the Activation status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of phone numbers with status", content =
            @Content(array = @ArraySchema(schema = @Schema(implementation = PhoneNumberData.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entry/Data not found",
                    content = @Content)})


    @GetMapping(value = "/phone_numbers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<PhoneNumber>> getAllPhoneNumbers() {

        return ResponseEntity.ok(directoryService.getAllPhoneNumbers());
    }



    @Operation(summary = "Add the customers and their phone numbers to the the phone book; If status of phone number is not provided ," +
            " it will be defaulted to NEW. Use the actovation API to actovate the phone numer ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adds all the customers and returns the corresponding Ids of the customer." +
                    " The same Ids should be used for fetching the details", content =
            @Content(array = @ArraySchema(schema = @Schema(implementation = PhoneNumberData.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid input supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Entry/Data not found",
                    content = @Content)})


    @PostMapping("/customers")
    public HttpStatus addCustomerWithPhoneNumbers(@RequestBody List<CustomerData> custData) {
        directoryService.addCustomer(custData);
        return HttpStatus.OK;

    }

}