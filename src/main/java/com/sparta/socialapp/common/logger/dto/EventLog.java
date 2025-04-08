package com.sparta.socialapp.common.logger.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
@Builder
public class EventLog {
    private String eventId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private ZonedDateTime timestamp;
    private String eventType;
    private String userId;
    private String clientIp;
    private String device;
    private String pageUrl;
    private Map<String, Object> info;
}
