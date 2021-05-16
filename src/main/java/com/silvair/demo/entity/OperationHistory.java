package com.silvair.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class OperationHistory {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Operation operation;

    private Double result;
    private LocalDateTime date;
    private HttpStatus status;
}
