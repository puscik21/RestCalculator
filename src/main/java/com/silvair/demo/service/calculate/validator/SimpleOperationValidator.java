package com.silvair.demo.service.calculate.validator;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import org.springframework.stereotype.Component;

@Component
public class SimpleOperationValidator implements OperationValidator {

    @Override
    public void validateData(Operation operation) throws OperationException {
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
}
