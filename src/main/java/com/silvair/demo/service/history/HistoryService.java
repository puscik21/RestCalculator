package com.silvair.demo.service.history;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.HistoryRecord;
import com.silvair.demo.repository.OperationRepository;
import com.silvair.demo.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    public List<HistoryRecord> getHistoryRecordsForLastPeriod(int minutes) {
        List<HistoryRecord> allRecords = historyRepository.findAll();
        LocalDateTime beginTime = LocalDateTime.now().minusMinutes(minutes);
        return allRecords.stream()
                .filter(record -> record.getDate().isAfter(beginTime))
                .collect(Collectors.toList());
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

    public QueriesStats getRequestStatistics() {
        List<HistoryRecord> records = getAllHistoryRecords();
        return QueriesStats.fromRecords(records);
    }

    public Map<String, QueriesStats> getRequestStatisticsForLastPeriod() {
        Map<String, QueriesStats> periodToHistoryRecords = new TreeMap<>();
        periodToHistoryRecords.put("last minute", getQueriesStatsForPeriod(1));
        periodToHistoryRecords.put("last hour", getQueriesStatsForPeriod(60));
        periodToHistoryRecords.put("last day", getQueriesStatsForPeriod(60 * 24));
        return periodToHistoryRecords;
    }

    private QueriesStats getQueriesStatsForPeriod(int period) {
        List<HistoryRecord> records = getHistoryRecordsForLastPeriod(period);
        return divideQueriesByPeriodTime(QueriesStats.fromRecords(records), period);
    }

    private QueriesStats divideQueriesByPeriodTime(QueriesStats queriesStats, int period) {
        divideStatsMapByPeriodTime(queriesStats.getPaths(), period);
        divideStatsMapByPeriodTime(queriesStats.getOperations(), period);
        return queriesStats;
    }

    private void divideStatsMapByPeriodTime(Map<String, Double> statsMap, int period) {
        statsMap.replaceAll((k, v) -> v = v / (period * 60));
    }
}
