package com.example.loganalytics.processor.messaging;

import com.example.loganalytics.common.grpc.LogEvent;
import com.example.loganalytics.processor.domain.ProcessedLog;
import com.example.loganalytics.processor.repository.ProcessedLogRepository;
import com.example.loganalytics.processor.processing.LogProcessingStrategy;
import com.example.loganalytics.processor.processing.ProcessingStrategyFactory;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LogConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);

    private final ProcessedLogRepository repository;
    private final ProcessingStrategyFactory strategyFactory;
    private final ExecutorService executorService;

    public LogConsumer(ProcessedLogRepository repository, ProcessingStrategyFactory strategyFactory) {
        this.repository = repository;
        this.strategyFactory = strategyFactory;
        this.executorService = Executors.newFixedThreadPool(10); // Worker pool
    }

    @KafkaListener(topics = "${kafka.topic.logs}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(byte[] message) {
        executorService.submit(() -> processMessage(message));
    }

    private void processMessage(byte[] message) {
        try {
            LogEvent event = LogEvent.parseFrom(message);

            ProcessedLog log = ProcessedLog.builder()
                    .serviceName(event.getServiceName())
                    .message(event.getMessage())
                    .level(event.getLevel())
                    .timestamp(event.getTimestamp())
                    .tags(new ArrayList<>())
                    .processed(false)
                    .build();

            // Apply strategies
            for (LogProcessingStrategy strategy : strategyFactory.getActiveStrategies()) {
                strategy.process(log);
            }

            log.setProcessed(true);
            repository.save(log);
            logger.debug("Processed and saved log from service: {}", log.getServiceName());

        } catch (InvalidProtocolBufferException e) {
            logger.error("Failed to parse Protobuf message", e);
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }
}
