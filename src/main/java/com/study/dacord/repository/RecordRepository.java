package com.study.dacord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.study.dacord.model.Record;

public interface RecordRepository extends JpaRepository<Record, Long>{

}
