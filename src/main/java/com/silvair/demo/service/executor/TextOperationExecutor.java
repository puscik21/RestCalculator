package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import org.springframework.stereotype.Component;

@Component
public class TextOperationExecutor implements OperationExecutor {

    @Override
    public boolean isProperType(OperationType operationType) {
        return operationType == OperationType.TEXT;
    }

    @Override
    public double executeOperation(Operation operation) throws OperationException {
        return 0;
    }
}
