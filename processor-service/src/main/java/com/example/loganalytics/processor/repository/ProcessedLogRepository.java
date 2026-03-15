package com.example.loganalytics.processor.repository;

import com.example.loganalytics.processor.domain.ProcessedLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedLogRepository extends MongoRepository<ProcessedLog, String> {
}
