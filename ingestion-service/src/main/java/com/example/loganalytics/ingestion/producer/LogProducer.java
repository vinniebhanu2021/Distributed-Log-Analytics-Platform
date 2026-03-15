package com.example.loganalytics.ingestion.producer;

import com.example.loganalytics.common.grpc.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducer {

    private static final Logger logger = LoggerFactory.getLogger(LogProducer.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Value("${kafka.topic.logs}")
    private String topic;

    public LogProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLog(LogEvent event) {
        logger.info("Publishing log event from service: {}", event.getServiceName());
        // Serialize protobuf to byte array
        kafkaTemplate.send(topic, event.toByteArray());
    }
}
