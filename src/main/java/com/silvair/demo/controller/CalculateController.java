package com.silvair.demo.controller;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.HistoryRecord;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class CalculateController {
    private final CalculateService calculateService;
    private final HistoryService historyService;

    public CalculateController(CalculateService calculateService, HistoryService historyService) {
        this.calculateService = calculateService;
        this.historyService = historyService;
    }

    @PostMapping("/calculate")
    public ResponseEntity<String> calculateOperation(@RequestBody Operation operation, HttpServletRequest request) {
        try {
            double result = calculateService.calculateOperation(operation);
            historyService.saveOperationHistoryRecord(operation, result, request.getRequestURI(), HttpStatus.OK.value());
            return ResponseEntity.ok(String.valueOf(result));
        } catch (OperationException e) {
            historyService.saveOperationHistoryRecord(operation, null, request.getRequestURI(), HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history")
    public List<HistoryRecord> getHistoryRecords(@RequestParam(required = false) Integer limit) {
        if (limit == null) {
            return historyService.getAllHistoryRecords();
        } else {
            return historyService.getNumberOfHistoryRecords(limit);
        }
    }

    @GetMapping("/history/sum")
    public Double sumNumberOfLastOperations(@RequestParam(required = false) Integer limit) {
        return historyService.sumOfLastOperations(limit);
    }

    @GetMapping("/statistics")
    public Map<String, Integer> getRequestStatistics() {
        return historyService.getRequestStatistics();
    }
}
