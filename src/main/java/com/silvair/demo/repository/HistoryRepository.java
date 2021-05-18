package com.silvair.demo.repository;

import com.silvair.demo.entity.HistoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryRecord, Long> {
}
