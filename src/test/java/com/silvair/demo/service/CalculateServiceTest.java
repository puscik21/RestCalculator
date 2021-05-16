package com.silvair.demo.service;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CalculateServiceTest {

    @Autowired
    private CalculateService calculateService;

    @Test
    void contextLoads() {
    }

    @Test
    void operationShouldBeAdded() {
        Operation operation = new Operation(10.0, 30.0, "", OperationType.ADD);
        assertEquals(40.0, calculateService.executeOperation(operation));
    }

    @Test
    void operationShouldBeSubtracted() {
        Operation operation = new Operation(11.0, 2.0, "", OperationType.SUBTRACT);
        assertEquals(9.0, calculateService.executeOperation(operation));
    }

    @Test
    void operationShouldBeMultiplied() {
        Operation operation = new Operation(2.0, 8.0, "", OperationType.MULTIPLY);
        assertEquals(16.0, calculateService.executeOperation(operation));
    }

    @Test
    void operationShouldBeDivided() {
        Operation operation = new Operation(15.0, 10.0, "", OperationType.DIVIDE);
        assertEquals(1.5, calculateService.executeOperation(operation));
    }
}
