package com.example.jpa1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data   // (lombok) getter, setter, equals, hashCode 메소드 자동 구현
@Entity // JPA Entity 클래스 앞
public class Department {

    @Id // 기본키 멤버 변수
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;
    String shortName;
    String phone;
}
