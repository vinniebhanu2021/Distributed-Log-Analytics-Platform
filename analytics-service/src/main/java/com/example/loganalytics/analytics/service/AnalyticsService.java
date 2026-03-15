package com.example.loganalytics.analytics.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AnalyticsService {

    private final MongoTemplate mongoTemplate;

    public AnalyticsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Map> getErrorsPerService() {
        Aggregation agg = newAggregation(
                match(Criteria.where("level").is("ERROR")),
                group("serviceName").count().as("errorCount"),
                project("errorCount").and("serviceName").previousOperation());
        return mongoTemplate.aggregate(agg, "processed_logs", Map.class).getMappedResults();
    }

    public List<Map> getLogCountByLevel() {
        Aggregation agg = newAggregation(
                group("level").count().as("count"),
                project("count").and("level").previousOperation());
        return mongoTemplate.aggregate(agg, "processed_logs", Map.class).getMappedResults();
    }

    public List<Map> getTopWarnings() {
        Aggregation agg = newAggregation(
                match(Criteria.where("level").is("WARN")),
                group("message").count().as("frequency"),
                sort(Sort.Direction.DESC, "frequency"),
                limit(10),
                project("frequency").and("message").previousOperation());
        return mongoTemplate.aggregate(agg, "processed_logs", Map.class).getMappedResults();
    }
}
