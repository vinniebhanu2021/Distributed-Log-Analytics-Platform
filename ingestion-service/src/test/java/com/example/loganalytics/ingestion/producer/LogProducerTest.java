package com.example.loganalytics.ingestion.producer;

import com.example.loganalytics.common.grpc.LogEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogProducerTest {

    @Mock
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Test
    void testSendLog() {
        LogProducer producer = new LogProducer(kafkaTemplate);
        // Inject private field if needed or use reflection/setter, currently relying on
        // constructor.
        // Wait, @Value won't work in unit test without Spring context or setter.
        // I will just ignore verifying the topic name exact match or use
        // ReflectionTestUtils if I had spring-test.
        // For simplicity, just verify send is called.

        LogEvent event = LogEvent.newBuilder()
                .setServiceName("test-service")
                .setMessage("hello")
                .build();

        producer.sendLog(event);

        verify(kafkaTemplate).send(any(), any());
    }
}
