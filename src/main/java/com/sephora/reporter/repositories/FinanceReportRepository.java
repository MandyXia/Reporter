package com.sephora.reporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sephora.reporter.entities.FinanceReport;

public interface FinanceReportRepository extends JpaRepository<FinanceReport, Integer> {

}
