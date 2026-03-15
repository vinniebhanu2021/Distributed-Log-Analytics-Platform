package com.example.loganalytics.analytics.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "processed_logs")
public class ProcessedLog {
    @Id
    private String id;
    private String serviceName;
    private String message;
    private String level;
    private long timestamp;

    public ProcessedLog() {
    }

    public ProcessedLog(String id, String serviceName, String message, String level, long timestamp) {
        this.id = id;
        this.serviceName = serviceName;
        this.message = message;
        this.level = level;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
