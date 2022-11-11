package com.study.dacord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.dacord.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long>{

	List<Record> findByTitle(String title);
	List<Record> findByTitleOrContent(String title, String content);
}
