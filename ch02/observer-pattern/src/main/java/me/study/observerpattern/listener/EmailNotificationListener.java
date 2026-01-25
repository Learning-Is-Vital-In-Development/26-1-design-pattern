package me.study.observerpattern.listener;

import me.study.observerpattern.event.UserRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener implements ApplicationListener<UserRegisteredEvent> {

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        System.out.println("[이메일 리스너] " + event.getEmail() + "로 환영 이메일 발송!");
    }
}
