package com.sparta.socialapp.domain.notification.controller;

import com.sparta.socialapp.common.api.ApiResponse;
import com.sparta.socialapp.domain.notification.dto.NotificationResponseDto;
import com.sparta.socialapp.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    // 읽지 않은 알람 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponseDto>>> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    // 알람 읽음 처리
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> convertNotificationIsReadStatus(@PathVariable Long id) {
        return notificationService.convertNotificationIsReadStatus(id);
    }
}
