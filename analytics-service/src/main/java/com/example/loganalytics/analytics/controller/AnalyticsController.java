package com.example.loganalytics.analytics.controller;

import com.example.loganalytics.analytics.service.AnalyticsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/metrics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/errors-per-service")
    public List<Map> getErrorsPerService() {
        return analyticsService.getErrorsPerService();
    }

    @GetMapping("/log-count")
    public List<Map> getLogCount() {
        return analyticsService.getLogCountByLevel();
    }

    @GetMapping("/top-warnings")
    public List<Map> getTopWarnings() {
        return analyticsService.getTopWarnings();
    }
}
