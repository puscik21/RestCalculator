package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;

public class SimpleOperationExecutor implements OperationExecutor {

    @Override
    public double executeOperation(Operation operation) {
        return 0;
    }

    @Override
    public boolean isDataValid(Operation operation) {
        return false;
    }

    public double add(double a, double b) {
        return 0;
    }

    public double subtract(double a, double b) {
        return 0;
    }

    public double multiply(double a, double b) {
        return 0;
    }

    public double divide(double a, double b) {
        return 0;
    }
}
