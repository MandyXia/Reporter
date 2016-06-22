package com.sephora.reporter.repositories;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sephora.reporter.entities.FinanceSource;

public interface FinanceSourceRepository extends JpaRepository<FinanceSource, Integer> {
	public FinanceSource findBySourceDate(Date sourceDate);
}
