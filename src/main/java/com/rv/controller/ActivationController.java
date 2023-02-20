package com.rv.controller;

import com.rv.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/phone-activation")
public class ActivationController {

    @Autowired
    private DirectoryService directoryService;

    /**
     * Activate a phone number
     *
     * @param phoneNumber
     * @return Status of the Phone Number
     */
    @PatchMapping(value = "/phone_numbers/{phoneNumber}/activate")
    public ResponseEntity activatePhoneNumber(@PathVariable final String phoneNumber) {
        directoryService.activatePhoneNumber(phoneNumber);
        return ResponseEntity.ok().build();
    }
}
