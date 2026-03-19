package com.corebank.corebank.repository;

import com.corebank.corebank.model.FraudReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FraudReportRepository extends JpaRepository<FraudReport, Long> {
    List<FraudReport> findByCardId(Long cardId);
}
