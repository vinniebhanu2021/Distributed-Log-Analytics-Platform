package com.example.loganalytics.ingestion.service;

import com.example.loganalytics.common.grpc.LogEvent;
import com.example.loganalytics.common.grpc.LogIngestionServiceGrpc;
import com.example.loganalytics.common.grpc.LogResponse;
import com.example.loganalytics.ingestion.producer.LogProducer;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class LogGrpcServiceImpl extends LogIngestionServiceGrpc.LogIngestionServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(LogGrpcServiceImpl.class);
    private final LogProducer logProducer;

    public LogGrpcServiceImpl(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @Override
    public void sendLog(LogEvent request, StreamObserver<LogResponse> responseObserver) {
        try {
            // Validate (basic)
            if (request.getServiceName().isEmpty()) {
                throw new IllegalArgumentException("Service Name is required");
            }

            // Push to queue/kafka
            logProducer.sendLog(request);

            LogResponse response = LogResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Log enqueued successfully")
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error processing log event", e);
            responseObserver.onError(e);
        }
    }
}
