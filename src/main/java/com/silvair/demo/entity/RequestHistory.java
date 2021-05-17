package com.silvair.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RequestHistory {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JsonIgnore
    private Operation operation;

    private Double result;
    private String path;
    private LocalDateTime date;
    private HttpStatus status;
}