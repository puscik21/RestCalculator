package com.silvair.demo.service.calculate.executor;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;

public interface OperationExecutor {
    boolean isProperType(OperationType operationType);

    double executeOperation(Operation operation) throws OperationException;
}
