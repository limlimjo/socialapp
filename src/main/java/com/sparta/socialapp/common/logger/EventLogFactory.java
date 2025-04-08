package com.sparta.socialapp.common.logger;

import com.sparta.socialapp.common.logger.dto.EventLog;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class EventLogFactory {
    public EventLog createEventLog(String eventType, String userId, String clientIp, String device, String pageUrl, Map<String, Object> info) {
        return EventLog.builder()
                .eventId(UUID.randomUUID().toString())
                .timestamp(ZonedDateTime.now())
                .eventType(eventType)
                .userId(userId)
                .clientIp(clientIp)
                .device(device)
                .pageUrl(pageUrl)
                .info(info)
                .build();
    }
}
