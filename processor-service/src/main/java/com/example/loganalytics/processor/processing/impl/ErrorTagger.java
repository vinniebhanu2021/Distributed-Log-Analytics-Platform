package com.example.loganalytics.processor.processing.impl;

import com.example.loganalytics.processor.domain.ProcessedLog;
import com.example.loganalytics.processor.processing.LogProcessingStrategy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("errorTagger")
public class ErrorTagger implements LogProcessingStrategy {
    @Override
    public void process(ProcessedLog log) {
        if ("ERROR".equalsIgnoreCase(log.getLevel())) {
            if (log.getTags() == null) {
                log.setTags(new ArrayList<>());
            }
            log.getTags().add("CRITICAL_ERROR");
        }
    }
}
