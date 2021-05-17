package com.silvair.demo.controller;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.RequestHistory;
import com.silvair.demo.exception.OperationException;
import com.silvair.demo.service.CalculateService;
import com.silvair.demo.service.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class CalculateController {
    private final CalculateService calculateService;
    private final HistoryService historyService;

    public CalculateController(CalculateService calculateService, HistoryService historyService) {
        this.calculateService = calculateService;
        this.historyService = historyService;
    }

    // TODO: 17.05.2021 add path to history
    @PostMapping("/calculate")
    public ResponseEntity<String> calculateOperation(@RequestBody Operation operation) {
        try {
            double result = calculateService.calculateOperation(operation);
            historyService.saveRequestHistory(operation, result, HttpStatus.OK);
            return ResponseEntity.ok(String.valueOf(result));
        } catch (OperationException e) {
            historyService.saveRequestHistory(operation, null, HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history")
    public List<RequestHistory> getAllLimitedRequestHistory(@RequestParam(required = false) Integer limit) {
        if (limit == null) {
            return historyService.findAll();
        } else {
            return historyService.getNumberOfRecords(limit);
        }
    }

    @GetMapping("/history/sum")
    public Double sumNumberOfLastOperations(@RequestParam(required = false) Integer limit) {
        return historyService.sumOfLastOperations(limit);
    }
}
