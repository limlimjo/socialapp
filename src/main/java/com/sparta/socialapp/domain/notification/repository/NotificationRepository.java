package com.sparta.socialapp.domain.notification.repository;

import com.sparta.socialapp.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
