package com.jojoldu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

//Posts 클래스로 Database를 접근하게 해줄 JpaRepository 생성
//인터페이스 생성 후, JpaRepository<Entity 클래스, PK 타입>를 상속하면 기본적인 CRUD 메소드가 자동으로 생성
public interface PostsRepository extends JpaRepository<Posts, Long> {
//    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
//    List<Posts> findAllDesc();
}
