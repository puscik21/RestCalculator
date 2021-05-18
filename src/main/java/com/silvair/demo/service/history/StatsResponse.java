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
public class StatsResponse {
    private Map<String, Long> paths;
    private Map<String, Long> operations;

    private StatsResponse() {
    }

    public static StatsResponse fromRecords(List<HistoryRecord> records) {
        StatsResponse statsResponse = new StatsResponse();
        statsResponse.setPaths(prepareSimplePaths(records));
        statsResponse.setOperations(prepareOperationTypes(records));
        return statsResponse;
    }

    private static Map<String, Long> prepareSimplePaths(List<HistoryRecord> records) {
        Map<String, Long> simplePaths = new HashMap<>();
        List<String> paths = records.stream().map(HistoryRecord::getPath).distinct().collect(Collectors.toList());
        paths.forEach(path -> {
            long queriesCount = records.stream()
                    .filter(record -> record.getPath().equals(path))
                    .count();
            simplePaths.put(path, queriesCount);
        });
        return simplePaths;
    }

    private static Map<String, Long> prepareOperationTypes(List<HistoryRecord> records) {
        Map<String, Long> operations = new HashMap<>();
        List<OperationType> operationTypes = Arrays.asList(OperationType.values());
        operationTypes.forEach(type -> {
            long queriesCount = records.stream()
                    .filter(record -> record.getOperation() != null)
                    .filter(record -> record.getOperation().getType() == type)
                    .count();
            operations.put(type.name(), queriesCount);
        });
        return operations;
    }
}
