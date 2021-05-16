package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;

public class TextOperationExecutor implements OperationExecutor {

    @Override
    public double executeOperation(Operation operation) {
        return 0;
    }

    @Override
    public boolean isDataValid(Operation operation) {
        return false;
    }
}
