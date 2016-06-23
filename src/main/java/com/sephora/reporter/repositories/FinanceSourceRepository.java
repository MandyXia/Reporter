package com.sephora.reporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sephora.reporter.entities.FinanceSource;

public interface FinanceSourceRepository extends JpaRepository<FinanceSource, Integer> {
	public FinanceSource findBySourceYearAndSourceMonth(int sourceYear, int sourceMonth);
}
