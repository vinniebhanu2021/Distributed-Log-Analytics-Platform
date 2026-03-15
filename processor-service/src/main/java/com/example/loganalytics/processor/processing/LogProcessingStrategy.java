package com.example.loganalytics.processor.processing;

import com.example.loganalytics.processor.domain.ProcessedLog;

public interface LogProcessingStrategy {
    void process(ProcessedLog log);
}
