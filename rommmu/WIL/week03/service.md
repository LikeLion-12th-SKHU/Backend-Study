# Service
<details>
<summary> service.posts.PostsService</summary>

``` java
package com.rommmu.book.springboot.service.posts;

import com.rommmu.book.springboot.domain.posts.Posts;
import com.rommmu.book.springboot.domain.posts.PostsRepository;
import com.rommmu.book.springboot.web.dto.PostsResponseDto;
import com.rommmu.book.springboot.web.dto.PostsSaveRequestDto;
import com.rommmu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id= " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }
}
```

</details>

### Spring Bean
스프링에서 Bean을 주입받는 방식은 세가지이다.
- @Autowired
- setter
- 생성자

이 중 생성자로 Bean 객체를 받으면 `@Autowired`와 동일한 효과를 받는다. 그리고 생성자는 롬북의 `@RequiredArgsConstructor`이 **final이 선언된 모든 필드를 인자값으로 하는 생성자**를 대신 생성해준다.
### 영속성 컨텍스트
**영속성 컨텍스트**란, **엔티티를 영구 저장하는 환경**으로 일종의 논리적인 개념이다. JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함되어있냐 아니냐로, 이에 따라 데이터베이스에 쿼리를 날리는 부분이 없을 수 있다.