package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;

public interface OperationExecutor {
    double executeOperation(Operation operation);
    boolean isDataValid(Operation operation);
}
