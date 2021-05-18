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
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class HistoryRecord {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JsonIgnore
    private Operation operation;

    private Double result;
    private String path;
    private LocalDateTime date;
    private Integer status;

    public Double getResultOrZero() {
        return Objects.requireNonNullElse(result, 0.0);
    }
}
