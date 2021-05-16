package com.silvair.demo.service.validator;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.exception.OperationException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TextOperationValidator implements OperationValidator {

    private final Pattern notAllowedSignPattern = Pattern.compile("[^0-9+\\-*\\/().\\s]+");

    @Override
    public void validateData(Operation operation) throws OperationException {
        checkMandatoryValues(operation);
        checkEvenQuantityOfBrackets(operation.getText());
        checkAllowedSigns(operation.getText());
    }

    private void checkMandatoryValues(Operation operation) throws OperationException {
        if (operation.getText() == null || operation.getText().isEmpty()) {
            throw new OperationException("Mandatory value 'text' can not be null or empty");
        }
    }

    private void checkEvenQuantityOfBrackets(String text) throws OperationException {
        long openingBracketsCount = text.chars().filter(ch -> ch == '(').count();
        long closingBracketsCount = text.chars().filter(ch -> ch == ')').count();
        if (openingBracketsCount != closingBracketsCount) {
            throw new OperationException("Number of opening brackets not equals number of closing brackets");
        }
    }

    private void checkAllowedSigns(String text) throws OperationException {
        Matcher matcher = notAllowedSignPattern.matcher(text);
        if (matcher.find()) {
            throw new OperationException("Text contains a not allowed sign");
        }
    }
}
