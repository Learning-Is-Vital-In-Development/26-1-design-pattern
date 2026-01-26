package me.study.observerpattern.listener;

import me.study.observerpattern.event.UserRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WelcomeBonusListener implements ApplicationListener<UserRegisteredEvent> {

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        System.out.println("[보너스 리스너] " + event.getUsername() + "님에게 1000 포인트 지급!");
    }
}
