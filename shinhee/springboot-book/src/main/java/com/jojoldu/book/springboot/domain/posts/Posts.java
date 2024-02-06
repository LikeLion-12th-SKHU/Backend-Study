package com.jojoldu.book.springboot.domain.posts;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*; //Entity, GeneratedValue, GeneratioType, Id

@Getter //롬복, 클래스 내 모든 필드의 Getter 메소드를 자동생성
@NoArgsConstructor  //롬복, 기본 생성자 자동 추가
@Entity //JPA의 어노테이션(주요 어노테이션을 클래스와 가깝게), 테이블과 링크될 클래스임을 나타냄.
public class Posts extends BaseTimeEntity {    //실제 DB 테이블과 매칭될 클래스

    @Id //해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성규칙
    // 옵션 추가 시 auto_increment가 됨. 테이블의 특정 컬럼(pk)에 대해 자동으로 값을 1씩 증가시켜주는 기능
    private Long id;

    // Column -> 테이블 칼럼, 선언하지 않아도 해당 클래스의 필드는 모두 칼럼이 되지만 기본값 외에 추가할 옵션이 있을 때 사용
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "Text", nullable = false)
    private String content;

    private String author;

    @Builder  // 롬복, 해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
    public void update(String title, String content){
        this.title = title;
        this.content = content;
}
}
