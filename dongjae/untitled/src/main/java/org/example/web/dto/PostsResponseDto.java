package org.example.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.posts.Posts;

@Getter
@AllArgsConstructor
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

    // 생성자를 직접 넘기는 대신 정적 팩토리 메소드를 사용할 수도 있음
    public PostsResponseDto of(Long id, String title, String content, String author) {
        return new PostsResponseDto(id, title, content, author);
    }

}
