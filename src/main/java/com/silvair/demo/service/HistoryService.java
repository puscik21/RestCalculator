package com.silvair.demo.service;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.RequestHistory;
import com.silvair.demo.repository.OperationRepository;
import com.silvair.demo.repository.HistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryService {

    private final OperationRepository operationRepository;
    private final HistoryRepository historyRepository;

    public HistoryService(OperationRepository operationRepository, HistoryRepository historyRepository) {
        this.operationRepository = operationRepository;
        this.historyRepository = historyRepository;
    }

    public void saveRequestHistory(Operation operation, Double result, HttpStatus status) {
        RequestHistory requestHistory = buildRequestHistory(operation, result, status);
        operationRepository.save(operation);
        historyRepository.save(requestHistory);
    }

    private RequestHistory buildRequestHistory(Operation operation, Double result, HttpStatus status) {
        RequestHistory requestHistory = new RequestHistory();
        requestHistory.setOperation(operation);
        requestHistory.setResult(result);
        requestHistory.setDate(LocalDateTime.now());
        requestHistory.setStatus(status);
        return requestHistory;
    }

    public List<RequestHistory> findAll() {
        return historyRepository.findAll();
    }

    public List<RequestHistory> getNumberOfRecords(int limit) {
        return historyRepository.findAll();
    }

    public Double sumOfLastOperations(Integer limit) {
        List<RequestHistory> requestHistories;
        if (limit == null) {
            requestHistories = findAll();
        } else {
            requestHistories = getNumberOfRecords(limit);
        }

        return 0.0;
    }
}
