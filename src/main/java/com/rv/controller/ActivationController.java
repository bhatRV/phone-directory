package com.rv.controller;

import com.rv.service.DirectoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/phone-activation")
@Api(value = "Phone Number Activation Controller" )

public class ActivationController {

    @Autowired
    private DirectoryService directoryService;

    @GetMapping(value = "/phone_numbers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllPhoneNumbers() {
        return ResponseEntity.ok(directoryService.getAllPhoneNumbers());
    }

    /**
     * Activate a phone number
     * @param phone_number_id
     * @return Status of the Phone Number
     */
    @PostMapping(value = "/phone_numbers/{phone_number_id}/activate")
    public ResponseEntity activatePhoneNumber(@PathVariable final Long phone_number_id) {
        directoryService.activatePhoneNumber(phone_number_id);
        return ResponseEntity.ok().build();
    }
}
