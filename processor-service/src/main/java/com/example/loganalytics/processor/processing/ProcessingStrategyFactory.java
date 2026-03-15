package com.example.loganalytics.processor.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProcessingStrategyFactory {

    private final Map<String, LogProcessingStrategy> strategies;

    @Autowired
    public ProcessingStrategyFactory(Map<String, LogProcessingStrategy> strategies) {
        this.strategies = strategies;
    }

    public List<LogProcessingStrategy> getActiveStrategies() {
        // In a real app, this could be dynamic based on config
        return List.of(
                strategies.get("timestampNormalizer"),
                strategies.get("errorTagger"));
    }
}
