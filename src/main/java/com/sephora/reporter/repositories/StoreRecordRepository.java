package com.sephora.reporter.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sephora.reporter.entities.StoreRecord;

public interface StoreRecordRepository extends JpaRepository<StoreRecord, Integer> {
	public List<StoreRecord> findByYearAndMonth(int year, int month);
}
