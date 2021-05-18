package com.silvair.demo.service;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.HistoryRecord;
import com.silvair.demo.repository.OperationRepository;
import com.silvair.demo.repository.HistoryRepository;
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

    public void saveOperationHistoryRecord(Operation operation, Double result, String path, Integer status) {
        HistoryRecord historyRecord = buildHistoryRecord(operation, result, path, status);
        operationRepository.save(operation);
        historyRepository.save(historyRecord);
    }

    public void saveHistoryRecord(String path, Integer status) {
        HistoryRecord historyRecord = buildHistoryRecord(null, null, path, status);
        historyRepository.save(historyRecord);
    }

    private HistoryRecord buildHistoryRecord(Operation operation, Double result, String path, Integer status) {
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setOperation(operation);
        historyRecord.setResult(result);
        historyRecord.setPath(path);
        historyRecord.setDate(LocalDateTime.now());
        historyRecord.setStatus(status);
        return historyRecord;
    }

    public List<HistoryRecord> getAllHistoryRecords() {
        return historyRepository.findAll();
    }

    public List<HistoryRecord> getNumberOfHistoryRecords(int limit) {
        List<HistoryRecord> allRecords = historyRepository.findAll();
        if (limit > allRecords.size()) {
            limit = allRecords.size();
        }
        return allRecords.subList(allRecords.size() - limit, allRecords.size());
    }

    public Double sumOfLastOperations(Integer limit) {
        List<HistoryRecord> requestHistories;
        if (limit == null) {
            requestHistories = getAllHistoryRecords();
        } else {
            requestHistories = getNumberOfHistoryRecords(limit);
        }

        return requestHistories.stream()
                .mapToDouble(HistoryRecord::getResultOrZero)
                .sum();
    }

    public StatsResponse getRequestStatistics() {
        List<HistoryRecord> records = getAllHistoryRecords();
        return StatsResponse.fromRecords(records);
    }
}
