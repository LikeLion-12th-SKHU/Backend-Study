package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // 자식 클래스에게 공통 매핑 정보를 상속 => 코드의 중복 줄임.
@EntityListeners(AuditingEntityListener.class)  // 필드 자동 업데이트
public abstract class BaseTimeEntity {

    @CreatedDate    // 생성 시간
    private LocalDateTime createdDate;

    @LastModifiedDate   //수정 시간
    private LocalDateTime modifiedDate;
}
