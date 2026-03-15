package com.example.loganalytics.ingestion.controller;

import com.example.loganalytics.common.grpc.LogEvent;
import com.example.loganalytics.ingestion.producer.LogProducer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestIngestionController {

    private final LogProducer logProducer;

    public TestIngestionController(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @PostMapping("/log")
    public String sendTestLog(@RequestBody Map<String, String> payload) {
        String serviceName = payload.getOrDefault("serviceName", "test-service");
        String message = payload.getOrDefault("message", "Test message via HTTP");
        String level = payload.getOrDefault("level", "INFO");

        LogEvent event = LogEvent.newBuilder()
                .setServiceName(serviceName)
                .setMessage(message)
                .setLevel(level)
                .setTimestamp(Instant.now().toEpochMilli())
                .build();

        logProducer.sendLog(event);
        return "Log pushed to Kafka via HTTP adapter!";
    }
}
