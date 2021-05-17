package com.silvair.demo.service;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.exception.OperationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HistoryServiceTest {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private CalculateService calculateService;

    private final int HistoryRecordsCount = 3;

    @BeforeAll
    void prepareFewHistoryRecords() throws OperationException {
        for (int i = 0; i < HistoryRecordsCount; i++) {
            Operation operation = Operation.textOperation("1 + 3");
            double result = calculateService.calculateOperation(operation);
            historyService.saveRequestHistory(operation, result, HttpStatus.OK);
        }
    }

    @Test
    void numberOfHistoryRecordsInDatabaseShouldMatch() {
        assertEquals(HistoryRecordsCount, historyService.findAll().size());
    }

    @Test
    void limitOfHistoryRecordsShouldWork() {
        assertEquals(2, historyService.getNumberOfRecords(2).size());
    }

    @Test
    void sumOfLastRecordsShouldBeCounted() {
        assertEquals(12.0, historyService.sumOfLastOperations(3));
    }
}