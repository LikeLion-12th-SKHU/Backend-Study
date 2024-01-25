package com.jojoldu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing  // BaseTimeEntity
@SpringBootApplication  // 스프링 부트의 자동설정, 스프링 Bean 읽기 및 생성 =>자동설정
public class Application {  // 앞으로 만들 프로젝트의 메인 클래스
    public static void main(String[] args) {
        // 내장 WAS 실행, 서버에 톰캣 설치팔 필요X
        SpringApplication.run(Application.class, args);
    }

}
