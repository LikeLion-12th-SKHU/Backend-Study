package org.example.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.posts.Posts;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    /***
     @NotNull: 주로 객체나 필드에 사용되며, 해당 값이 null이 아닌지를 검증합니다.
     @NotBlank: 주로 문자열 필드에 사용되며, 공백을 제외한 최소한 한 글자 이상의 문자열이어야 합니다.
     @NotEmpty: 주로 문자열, 컬렉션, 배열 등에 사용되며, null이 아니고 비어 있지 않은 값을 가져야 합니다. 공백만으로 이루어진 문자열도 허용됩니다.
     */

    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
