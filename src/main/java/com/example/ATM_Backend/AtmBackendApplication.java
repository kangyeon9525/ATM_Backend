package com.example.ATM_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

// 어플리케이션 메인 클래스
@SpringBootApplication
@EnableScheduling
public class AtmBackendApplication {

	// 메인 메소드 (애플리케이션 실행 진입점)
	public static void main(String[] args) {
		SpringApplication.run(AtmBackendApplication.class, args);
	}

}
