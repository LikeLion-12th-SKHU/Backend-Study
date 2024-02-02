# Dto
<details>
<summary> web.dto.posts.PostsSaveRequestDto</summary>

``` java
package com.rommmu.book.springboot.web.dto;

import com.rommmu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
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
```

</details>

> Dto는 View를 위한 클래스로, View Layer와 DB Layer의 역할을 분리해야한다. 따라서 `Entity` 클래스와 `Controller`에서 사용할 Dto는 분리해서 사용해야한다.

## Test Code
<details>
<summary> web.PostsApiControllerTest</summary>

``` java
package com.rommmu.book.springboot.web.dto;

import com.rommmu.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
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
```

</details>

### `@WebMvcTest`
Controller와 ControllerAdvice 등 **외부 연동과 관련된 부분만 활성화**되기 때문에, JPA 기능이 작동하지 않는다. 
### `@SpringBootTest`
JPA 기능까지 한 번에 테스트할때는 `@SpringBootTest`와 `TestRestTemplate`를 사용해야한다.
