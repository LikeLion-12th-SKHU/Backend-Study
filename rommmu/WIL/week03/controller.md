# Controller
<details>
<summary> web.PostsApiController</summary>

``` java
package com.rommmu.book.springboot.web;

import com.rommmu.book.springboot.service.posts.PostsService;
import com.rommmu.book.springboot.web.dto.PostsResponseDto;
import com.rommmu.book.springboot.web.dto.PostsSaveRequestDto;
import com.rommmu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {

        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
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
