package com.silvair.demo.service.history;

import com.silvair.demo.entity.HistoryRecord;
import com.silvair.demo.entity.OperationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class QueriesStats {
    private Map<String, Double> paths;
    private Map<String, Double> operations;

    private QueriesStats() {
    }

    public static QueriesStats fromRecords(List<HistoryRecord> records) {
        QueriesStats queriesStats = new QueriesStats();
        queriesStats.setPaths(prepareSimplePaths(records));
        queriesStats.setOperations(prepareOperationTypes(records));
        return queriesStats;
    }

    private static Map<String, Double> prepareSimplePaths(List<HistoryRecord> records) {
        Map<String, Double> simplePaths = new HashMap<>();
        List<String> paths = records.stream().map(HistoryRecord::getPath).distinct().collect(Collectors.toList());
        paths.forEach(path -> {
            long queriesCount = records.stream()
                    .filter(record -> record.getPath().equals(path))
                    .count();
            simplePaths.put(path, (double) queriesCount);
        });
        return simplePaths;
    }

    private static Map<String, Double> prepareOperationTypes(List<HistoryRecord> records) {
        Map<String, Double> operations = new HashMap<>();
        List<OperationType> operationTypes = Arrays.asList(OperationType.values());
        operationTypes.forEach(type -> {
            long queriesCount = records.stream()
                    .filter(record -> record.getOperation() != null)
                    .filter(record -> record.getOperation().getType() == type)
                    .count();
            operations.put(type.name(), (double) queriesCount);
        });
        return operations;
    }
}
