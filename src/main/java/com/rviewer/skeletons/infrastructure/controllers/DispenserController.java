package com.rviewer.skeletons.infrastructure.controllers;

import com.rviewer.skeletons.domain.dto.DispenserCreateRequest;
import com.rviewer.skeletons.domain.dto.DispenserStatusRequest;
import com.rviewer.skeletons.domain.model.Dispenser;
import com.rviewer.skeletons.domain.responses.DispenserRevenue;
import com.rviewer.skeletons.domain.services.DispenserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispenser")
public class DispenserController {
    private static final Logger logger = LoggerFactory.getLogger(DispenserController.class);

    private DispenserService dispenserService;

    public DispenserController(DispenserService dispenserService) {
        this.dispenserService = dispenserService;
    }

    @PostMapping
    public ResponseEntity<Dispenser> createDispenser(@Validated @RequestBody DispenserCreateRequest createRequest) {
        Dispenser dispenser = dispenserService.createDispenser(createRequest);
        logger.debug("Dispenser created correctly with id: {}", dispenser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(dispenser);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestBody DispenserStatusRequest dispenserStatusRequest) {

        logger.debug("Status of the tap changed correctly to : " + dispenserStatusRequest.getStatus());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}/spending")
    public ResponseEntity<DispenserRevenue> getDispenserRevenue(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
