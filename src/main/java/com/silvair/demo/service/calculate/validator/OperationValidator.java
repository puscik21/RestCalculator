package com.silvair.demo.service.calculate.validator;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.exception.OperationException;

public interface OperationValidator {

    void validateData(Operation operation) throws OperationException;
}
