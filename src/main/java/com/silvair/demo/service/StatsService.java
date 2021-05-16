package com.silvair.demo.service;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationHistory;
import com.silvair.demo.repository.OperationRepository;
import com.silvair.demo.repository.StatsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatsService {

    private final OperationRepository operationRepository;
    private final StatsRepository statsRepository;

    public StatsService(OperationRepository operationRepository, StatsRepository statsRepository) {
        this.operationRepository = operationRepository;
        this.statsRepository = statsRepository;
    }

    public void saveOperationHistory(Operation operation, Double result, HttpStatus status) {
        OperationHistory operationHistory = buildOperationHistory(operation, result, status);
        operationRepository.save(operation);
        statsRepository.save(operationHistory);
    }

    private OperationHistory buildOperationHistory(Operation operation, Double result, HttpStatus status) {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setOperation(operation);
        operationHistory.setResult(result);
        operationHistory.setDate(LocalDateTime.now());
        operationHistory.setStatus(status);
        return operationHistory;
    }
}
