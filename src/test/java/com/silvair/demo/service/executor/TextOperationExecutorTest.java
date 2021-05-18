package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.exception.OperationException;
import com.silvair.demo.service.calculate.executor.TextOperationExecutor;
import com.silvair.demo.service.calculate.validator.TextOperationValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextOperationExecutorTest {

    private final TextOperationValidator textOperationValidator = new TextOperationValidator();
    private final TextOperationExecutor textOperationExecutor = new TextOperationExecutor(textOperationValidator);

    @Test
    void simpleAddShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("2+2");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(4, result, 0.001);
    }

    @Test
    void negativeSumShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("2 - -6");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(8, result, 0.001);
    }

    @Test
    void manySpacesShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("  2  -   -6   ");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(8, result, 0.001);
    }

    @Test
    void negativeMultiplicationShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("12*-1");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(-12, result, 0.001);
    }

    @Test
    void manybracketsShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("((80 - (19)))");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(61, result, 0.001);
    }

    @Test
    void manyNegationsShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("(1 - 2) + -(-(-(-4)))");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(3, result, 0.001);
    }

    @Test
    void operationsShouldBeInCorrectOrder() throws OperationException {
        Operation operation = Operation.textOperation("2 + 2 * 2");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(6, result, 0.001);
    }

    @Test
    void complicatedTestShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("((2.33 / (2.9+3.5)*4) - -6)");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(7.45625, result, 0.000001);
    }

    @Test
    void evenMoreComplicatedTestShouldPass() throws OperationException {
        Operation operation = Operation.textOperation("123.45*(678.90 / (-2.5+ 11.5)-(80 -19) *33.25) / 20 + 11");
        double result = textOperationExecutor.executeOperation(operation);
        assertEquals(-12042.760875, result, 0.0000001);
    }

    @Test
    void shouldThrowExceptionForNullTextVariable() throws OperationException {
        Operation operation = Operation.textOperation(null);
        assertThrows(OperationException.class, () -> textOperationExecutor.executeOperation(operation));
    }

    @Test
    void shouldThrowExceptionForEmptyTextVariable() throws OperationException {
        Operation operation = Operation.textOperation("");
        assertThrows(OperationException.class, () -> textOperationExecutor.executeOperation(operation));
    }

    @Test
    void shouldThrowExceptionForOddQuantityOfBrackets() throws OperationException {
        Operation operation = Operation.textOperation("(2.0 + 5.0))");
        assertThrows(OperationException.class, () -> textOperationExecutor.executeOperation(operation));
    }

    @Test
    void shouldThrowExceptionForNotAllowedSign() throws OperationException {
        Operation operation = Operation.textOperation("(2.0 % 5.0)");
        assertThrows(OperationException.class, () -> textOperationExecutor.executeOperation(operation));
    }
}