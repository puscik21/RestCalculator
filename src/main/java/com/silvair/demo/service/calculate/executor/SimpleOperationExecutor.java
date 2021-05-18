package com.silvair.demo.service.calculate.executor;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import com.silvair.demo.service.calculate.validator.OperationValidator;
import com.silvair.demo.service.calculate.validator.SimpleOperationValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SimpleOperationExecutor implements OperationExecutor {
    private final OperationValidator operationValidator;

    private final List<OperationType> operationTypes = List.of(OperationType.ADD, OperationType.SUBTRACT, OperationType.MULTIPLY, OperationType.DIVIDE);

    public SimpleOperationExecutor(SimpleOperationValidator simpleOperationValidator) {
        this.operationValidator = simpleOperationValidator;
    }

    @Override
    public boolean isProperType(OperationType operationType) {
        return operationTypes.contains(operationType);
    }

    @Override
    public double executeOperation(Operation operation) throws OperationException {
        operationValidator.validateData(operation);
        return useProperOperation(operation);
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
