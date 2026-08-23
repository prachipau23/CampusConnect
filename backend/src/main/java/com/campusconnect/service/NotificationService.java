package com.campusconnect.service;

import com.campusconnect.entity.Notification;
import com.campusconnect.entity.User;
import com.campusconnect.repository.NotificationRepository;
import com.campusconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public long getUnreadCount(User user) {
        return notificationRepository.countByUserAndUnreadTrue(user);
    }

    @Transactional
    public void sendNotification(User recipient, String title, String message, String icon, String type) {
        if (recipient == null) return;
        Notification n = new Notification(recipient, title, message, icon != null ? icon : "🔔", type != null ? type : "GENERAL");
        notificationRepository.save(n);
    }

    @Transactional
    public void sendNotificationToAll(String title, String message, String icon, String type) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            sendNotification(user, title, message, icon, type);
        }
    }

    @Transactional
    public void toggleRead(Long notifId, User user) {
        Notification n = notificationRepository.findById(notifId).orElse(null);
        if (n != null && n.getUser().getId().equals(user.getId())) {
            n.setUnread(!n.isUnread());
            notificationRepository.save(n);
        }
    }

    @Transactional
    public void markAllRead(User user) {
        List<Notification> list = notificationRepository.findByUserOrderByCreatedAtDesc(user);
        for (Notification n : list) {
            n.setUnread(false);
        }
        notificationRepository.saveAll(list);
    }
}
