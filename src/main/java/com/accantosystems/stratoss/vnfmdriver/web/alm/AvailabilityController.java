package com.accantosystems.stratoss.vnfmdriver.web.alm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;


@RestController("AvailabilityController")
@RequestMapping("/api")
public class AvailabilityController {

    @GetMapping
    @Operation(summary = "Test Availability", description = "Returns a string indicating the current health and availability of the driver")
    public ResponseEntity<String> testAvailability() {
        return ResponseEntity.ok("OK");
    }

}
