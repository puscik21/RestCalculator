package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleOperationExecutor implements OperationExecutor {

    private final List<OperationType> operationTypes = List.of(OperationType.ADD, OperationType.SUBTRACT, OperationType.MULTIPLY, OperationType.DIVIDE);

    @Override
    public boolean isProperType(OperationType operationType) {
        return operationTypes.contains(operationType);
    }

    @Override
    public double executeOperation(Operation operation) throws OperationException {
        validateData(operation);
        return useProperOperation(operation);
    }

    private void validateData(Operation operation) throws OperationException {
        checkMandatoryValues(operation);
        checkDivisionByZero(operation);
    }

    private void checkMandatoryValues(Operation operation) throws OperationException {
        if (operation.getA() == null || operation.getB() == null) {
            throw new OperationException("Mandatory value is null");
        }
    }

    private void checkDivisionByZero(Operation operation) throws OperationException {
        if (operation.getType() == OperationType.DIVIDE && operation.getB() == 0) {
            throw new OperationException("Division by 0");
        }
    }

    private double useProperOperation(Operation operation) throws OperationException {
        switch (operation.getType()) {
            case ADD:
                return add(operation.getA(), operation.getB());
            case SUBTRACT:
                return subtract(operation.getA(), operation.getB());
            case MULTIPLY:
                return multiply(operation.getA(), operation.getB());
            case DIVIDE:
                return divide(operation.getA(), operation.getB());
            default:
                throw new OperationException("Could not calculate operation of type " + operation.getType().name());
        }
    }

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        return a / b;
    }
}
