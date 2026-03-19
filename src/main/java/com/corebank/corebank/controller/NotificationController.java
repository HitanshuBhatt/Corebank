package com.corebank.corebank.controller;

import com.corebank.corebank.model.Notification;
import com.corebank.corebank.model.User;
import com.corebank.corebank.repository.UserRepository;
import com.corebank.corebank.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserRepository userRepo;

    public NotificationController(NotificationService notificationService,
                                  UserRepository userRepo) {
        this.notificationService = notificationService;
        this.userRepo = userRepo;
    }

    private Long getUserId(Principal principal) {
        User u = userRepo.findByUsername(principal.getName()).orElse(null);
        return u != null ? u.getId() : null;
    }

    @GetMapping("")
    public List<Notification> getMyNotifications(Principal principal) {
        return notificationService.getForUser(getUserId(principal));
    }

    @GetMapping("/unread-count")
    public long getUnreadCount(Principal principal) {
        return notificationService.getUnreadCount(getUserId(principal));
    }

    @PostMapping("/mark-read")
    public String markRead(Principal principal) {
        notificationService.markAllRead(getUserId(principal));
        return "OK";
    }
}
