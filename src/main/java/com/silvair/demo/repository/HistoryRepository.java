package com.silvair.demo.repository;

import com.silvair.demo.entity.RequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<RequestHistory, Long> {
}
