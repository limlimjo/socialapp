package com.sparta.socialapp.common.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.socialapp.common.logger.dto.EventLog;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EventLogger {

    private final ObjectMapper objectMapper;

    private static final Logger clickLogger = LoggerFactory.getLogger("com.sparta.socialapp.log.click");
    private static final Logger wishLogger = LoggerFactory.getLogger("com.sparta.socialapp.log.wish");
    //private static final Logger alarmLogger = LoggerFactory.getLogger("com.sparta.socialapp.log.alarm");
    //private static final Logger accessLogger = LoggerFactory.getLogger("com.sparta.socialapp.log.access");

    public void logClickEvent(EventLog log) {
        logJson(clickLogger, log);
    }

    public void logWishEvent(EventLog log) {
        logJson(wishLogger, log);
    }

//    public static void logAlarmEvent(EventLog log) {
//        logJson(alarmLogger, log);
//    }
//
//    public static void logAccessEvent(EventLog log) {
//        logJson(accessLogger, log);
//    }

    private void logJson(Logger logger, Object logObject) {
        try {
            String jsonLog = objectMapper.writeValueAsString(logObject);
            logger.info(jsonLog);
        } catch (Exception e) {
            logger.error("로그 직렬화 에러", e);
        }
    }
}
