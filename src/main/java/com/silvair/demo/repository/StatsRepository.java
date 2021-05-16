package com.silvair.demo.repository;

import com.silvair.demo.entity.OperationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatsRepository extends JpaRepository<OperationHistory, Long> {
}
