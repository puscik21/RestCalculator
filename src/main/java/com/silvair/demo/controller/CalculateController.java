package com.silvair.demo.controller;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.exception.OperationException;
import com.silvair.demo.service.CalculateService;
import com.silvair.demo.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
public class CalculateController {
    private final CalculateService calculateService;
    private final StatsService statsService;

    public CalculateController(CalculateService calculateService, StatsService statsService) {
        this.calculateService = calculateService;
        this.statsService = statsService;
    }

    @PostMapping
    public ResponseEntity<String> calculateOperation(@RequestBody Operation operation) {
        try {
            double result = calculateService.calculateOperation(operation);
            statsService.saveOperationHistory(operation, result, HttpStatus.OK);
            return ResponseEntity.ok(String.valueOf(result));
        } catch (OperationException e) {
            statsService.saveOperationHistory(operation, null, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
