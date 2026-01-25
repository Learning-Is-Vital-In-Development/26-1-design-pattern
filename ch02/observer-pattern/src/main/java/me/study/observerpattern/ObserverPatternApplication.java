package me.study.observerpattern;

import me.study.observerpattern.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ObserverPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObserverPatternApplication.class, args);
	}

	@Bean
	CommandLineRunner demo(UserService userService) {
		return args -> {
			userService.registerUser("홍길동", "hong@example.com");
			userService.registerUser("김철수", "kim@example.com");
		};
	}
}
