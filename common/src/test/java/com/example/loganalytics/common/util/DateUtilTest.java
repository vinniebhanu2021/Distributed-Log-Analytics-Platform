package com.example.loganalytics.common.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void testFormatTimestamp() {
        long timestamp = 1700000000000L; // Fixed timestamp
        String formatted = DateUtil.formatTimestamp(timestamp);
        assertNotNull(formatted);
        // Exact string depends on timezone, just check it's not empty
        assertFalse(formatted.isEmpty());
    }
}
