package com.silvair.demo.controller;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.exception.OperationException;
import com.silvair.demo.service.CalculateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculate")
public class CalculateController {
    private final CalculateService calculateService;

    public CalculateController(CalculateService calculateService) {
        this.calculateService = calculateService;
    }

    @PostMapping
    public double calculateOperation(@RequestBody Operation operation) throws OperationException {
        return calculateService.calculateOperation(operation);
    }
}
