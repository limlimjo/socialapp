package com.sparta.socialapp.domain.notification.repository;

import com.sparta.socialapp.domain.notification.entity.Notification;
import com.sparta.socialapp.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // 수신자의 읽음 확인
    List<Notification> findByRecipientAndIsReadFalse(User recipient);
}
