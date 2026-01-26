package me.study.observerpattern.service;

import me.study.observerpattern.event.UserRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final ApplicationEventPublisher eventPublisher;

    public UserService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void registerUser(String username, String email) {
        System.out.println("\n=== 사용자 등록: " + username + " ===");

        // 이벤트 발행 → 모든 Observer에게 알림
        eventPublisher.publishEvent(new UserRegisteredEvent(this, username, email));

        System.out.println("=== 등록 완료 ===\n");
    }
}
