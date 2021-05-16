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
    void operationShouldBeAdded() throws OperationException {
        Operation operation = new Operation(10.0, 30.0, "", OperationType.ADD);
        assertEquals(40.0, calculateService.calculateOperation(operation));
    }

    @Test
    void operationShouldBeSubtracted() throws OperationException {
        Operation operation = new Operation(11.0, 2.0, "", OperationType.SUBTRACT);
        assertEquals(9.0, calculateService.calculateOperation(operation));
    }

    @Test
    void operationShouldBeMultiplied() throws OperationException {
        Operation operation = new Operation(2.0, 8.0, "", OperationType.MULTIPLY);
        assertEquals(16.0, calculateService.calculateOperation(operation));
    }

    @Test
    void operationShouldBeDivided() throws OperationException {
        Operation operation = new Operation(15.0, 10.0, "", OperationType.DIVIDE);
        assertEquals(1.5, calculateService.calculateOperation(operation));
    }


    @Test
    void shouldThrowExceptionForDivisionByZero() {
        Operation operation = new Operation(10.0, 0.0, "", OperationType.DIVIDE);
        assertThrows(OperationException.class, () -> calculateService.calculateOperation(operation));
    }

    @Test
    void shouldThrowExceptionForNullAVariable() {
        Operation operation = new Operation(null, 30.0, "", OperationType.ADD);
        assertThrows(OperationException.class, () -> calculateService.calculateOperation(operation));
    }

    @Test
    void shouldThrowExceptionForNullBVariable() {
        Operation operation = new Operation(10.0, null, "", OperationType.ADD);
        assertThrows(OperationException.class, () -> calculateService.calculateOperation(operation));
    }

    @Test
    void shouldThrowExceptionForNullTypeVariable() {
        Operation operation = new Operation(10.0, 30.0, "", null);
        assertThrows(OperationException.class, () -> calculateService.calculateOperation(operation));
    }
}
