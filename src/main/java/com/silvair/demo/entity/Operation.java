package com.silvair.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "operation")
    private OperationHistory history;

    private Double a;
    private Double b;
    private String text;
    private OperationType type;

    public Operation(Double a, Double b, String text, OperationType type) {
        this.a = a;
        this.b = b;
        this.text = text;
        this.type = type;
    }

    public static Operation textOperation(String text) {
        Operation operation = new Operation();
        operation.setText(text);
        operation.setType(OperationType.TEXT);
        return operation;
    }
}
