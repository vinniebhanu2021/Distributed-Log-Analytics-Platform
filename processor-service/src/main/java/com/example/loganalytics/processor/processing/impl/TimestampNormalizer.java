package com.example.loganalytics.processor.processing.impl;

import com.example.loganalytics.processor.domain.ProcessedLog;
import com.example.loganalytics.processor.processing.LogProcessingStrategy;
import org.springframework.stereotype.Component;

@Component("timestampNormalizer")
public class TimestampNormalizer implements LogProcessingStrategy {
    @Override
    public void process(ProcessedLog log) {
        if (log.getTimestamp() <= 0) {
            log.setTimestamp(System.currentTimeMillis());
        }
    }
}
