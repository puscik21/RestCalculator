package com.silvair.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Operation {
    private Double a;
    private Double b;
    private String text;
    private OperationType type;

    public static Operation textOperation(String text) {
        return new Operation(null, null, text, OperationType.TEXT);
    }
}
