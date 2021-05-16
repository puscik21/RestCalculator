package com.silvair.demo.service;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import com.silvair.demo.service.executor.OperationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculateService {
    List<OperationExecutor> operationExecutors;

    public CalculateService(List<OperationExecutor> operationExecutors) {
        this.operationExecutors = operationExecutors;
    }

    public double calculateOperation(Operation operation) throws OperationException {
        if (operation.getType() == null) {
            throw new OperationException("Operation type can not be null");
        }
        return getProperExecutor(operation.getType()).executeOperation(operation);
    }

    private OperationExecutor getProperExecutor(OperationType operationType) throws OperationException {
        return operationExecutors.stream()
                .filter(e -> e.isProperType(operationType))
                .findAny()
                .orElseThrow(() -> new OperationException("Could not calculate operation of type " + operationType.name()));
    }
}
