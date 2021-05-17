package com.silvair.demo.service.executor;

import com.silvair.demo.entity.Operation;
import com.silvair.demo.entity.OperationType;
import com.silvair.demo.exception.OperationException;
import com.silvair.demo.service.validator.OperationValidator;
import com.silvair.demo.service.validator.TextOperationValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TextOperationExecutor implements OperationExecutor {
    private final OperationValidator operationValidator;

    public TextOperationExecutor(TextOperationValidator textOperationValidator) {
        this.operationValidator = textOperationValidator;
    }

    @Override
    public boolean isProperType(OperationType operationType) {
        return operationType == OperationType.TEXT;
    }

    @Override
    public double executeOperation(Operation operation) throws OperationException {
        operationValidator.validateData(operation);
        return calculate(operation.getText());
    }

    private double calculate(String expression) {
        expression = expression.replace(" ", "");
        int startBracket = 0;
        int i = 0;
        while (i < expression.length()) {
            if (expression.charAt(i) == '(') {
                startBracket = i;
            } else if (expression.charAt(i) == ')') {
                expression = calculateBrackets(expression, startBracket, i + 1);
                i = -1;
            }
            i++;
        }
        return Double.parseDouble(calculateExpression(expression));
    }

    private String calculateBrackets(String expression, int start, int end) {
        String bracket = expression.substring(start, end);
        String result = calculateExpression(bracket.substring(1, bracket.length() - 1));
        return expression.replace(bracket, result);
    }

    private String calculateExpression(String expression) {
        expression = expression.replace("--", "+");
        expression = expression.replace("+-", "-");
        expression = expression.replace("-+", "-");
        expression = expression.replaceAll("\\++", "+");
        expression = expression.replaceAll("\\*\\++", "*");
        expression = expression.replaceAll("\\/\\++", "/");
        String firstOrderRegex = SimpleEquation.SIGNED_DOUBLE + SimpleEquation.FIRST_ORDER_SIGNS + SimpleEquation.SIGNED_DOUBLE;
        String secondOrderRegex = SimpleEquation.SIGNED_DOUBLE + SimpleEquation.SECOND_ORDER_SIGNS + SimpleEquation.DOUBLE;
        expression = calculateWithRegex(expression, firstOrderRegex);
        expression = calculateWithRegex(expression, secondOrderRegex);
        return expression;
    }

    private String calculateWithRegex(String expression, String regex) {
        Pattern singleEquation = Pattern.compile(regex);
        Matcher matcher = singleEquation.matcher(expression);

        while (matcher.find()) {
            SimpleEquation simpleEquation = new SimpleEquation(matcher.group(1), matcher.group(2), matcher.group(3));
            String result = simpleEquation.calculate();
            expression = expression.replace(simpleEquation.equation, result);
            matcher = singleEquation.matcher(expression);
        }
        return expression;
    }

    private static class SimpleEquation {
        private final static String FIRST_ORDER_SIGNS = "([*,/])";
        private final static String SECOND_ORDER_SIGNS = "([+,-])";
        private final static String DOUBLE = "(\\d+\\.*\\d*)";
        private final static String SIGNED_DOUBLE = "(\\-?\\d+\\.*\\d*)";
        private final String leftNumber;
        private final String sign;
        private final String rightNumber;
        private final String equation;

        private SimpleEquation(String leftNumber, String sign, String rightNumber) {
            this.leftNumber = leftNumber;
            this.sign = sign;
            this.rightNumber = rightNumber;
            this.equation = leftNumber + sign + rightNumber;
        }

        private String calculate() {
            double left = Double.parseDouble(leftNumber);
            double right = Double.parseDouble(rightNumber);
            switch (sign) {
                case "*":
                    return "" + (left * right);
                case "/":
                    return "" + (left / right);
                case "+":
                    return "" + (left + right);
                case "-":
                    return "" + (left - right);
                default:
                    return "";
            }
        }
    }
}
