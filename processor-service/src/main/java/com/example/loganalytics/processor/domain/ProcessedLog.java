package com.example.loganalytics.processor.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "processed_logs")
@CompoundIndexes({
        @CompoundIndex(name = "ts_svc_lvl_idx", def = "{'timestamp': 1, 'serviceName': 1, 'level': 1}")
})
public class ProcessedLog {

    @Id
    private String id;

    private String serviceName;
    private String message;
    private String level;
    private long timestamp;

    private List<String> tags;
    private boolean processed;

    public ProcessedLog() {
    }

    public ProcessedLog(String id, String serviceName, String message, String level, long timestamp, List<String> tags,
            boolean processed) {
        this.id = id;
        this.serviceName = serviceName;
        this.message = message;
        this.level = level;
        this.timestamp = timestamp;
        this.tags = tags;
        this.processed = processed;
    }

    public static Builder builder() {
        return new Builder();
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public static class Builder {
        private String id;
        private String serviceName;
        private String message;
        private String level;
        private long timestamp;
        private List<String> tags;
        private boolean processed;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder level(String level) {
            this.level = level;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder processed(boolean processed) {
            this.processed = processed;
            return this;
        }

        public ProcessedLog build() {
            return new ProcessedLog(id, serviceName, message, level, timestamp, tags, processed);
        }
    }
}
