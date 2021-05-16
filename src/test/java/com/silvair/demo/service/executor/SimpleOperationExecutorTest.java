package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleOperationExecutorTest {

    private final SimpleOperationExecutor simpleOperationExecutor = new SimpleOperationExecutor();

    @Test
    void numbersShouldBeAdded() {
        assertEquals(5.0, simpleOperationExecutor.add(2.0, 3.0));
    }

    @Test
    void numbersShouldBeSubtracted() {
        assertEquals(-1.0, simpleOperationExecutor.subtract(2.0, 3.0));
    }

    @Test
    void numbersShouldBeMultiplied() {
        assertEquals(6.0, simpleOperationExecutor.multiply(2.0, 3.0));
    }

    @Test
    void numbersShouldBeDivided() {
        assertEquals(1.5, simpleOperationExecutor.divide(3.0, 2.0));
    }

    @Test
    void shouldThrowExceptionForWrongOperationType() {
        Operation operation = new Operation(10.0, 30.0, "", OperationType.TEXT);
        assertThrows(OperationException.class, () -> simpleOperationExecutor.executeOperation(operation));
    }
}