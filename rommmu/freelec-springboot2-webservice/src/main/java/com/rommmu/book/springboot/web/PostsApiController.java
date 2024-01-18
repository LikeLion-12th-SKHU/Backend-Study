package com.rommmu.book.springboot.web;

import com.rommmu.book.springboot.commom.dto.BaseResponse;
import com.rommmu.book.springboot.exception.SuccessCode;
import com.rommmu.book.springboot.service.PostsService;
import com.rommmu.book.springboot.web.dto.PostsResponseDto;
import com.rommmu.book.springboot.web.dto.PostsSaveRequestDto;
import com.rommmu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED) // 기본 상태코드로 전환한다.
    public BaseResponse<Long> save(@RequestBody PostsSaveRequestDto requestDto) {
        final Long data = postsService.save(requestDto);
        return BaseResponse.success(SuccessCode.POST_SAVE_SUCCESS, data);
    }

    @PutMapping("/api/v1/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        final Long data = postsService.update(id, requestDto);
        return BaseResponse.success(SuccessCode.POST_UPDATE_SUCCESS, data);
    }

    @GetMapping("/api/v1/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PostsResponseDto> findById(@PathVariable Long id) {
        final PostsResponseDto data = postsService.findById(id);
        return BaseResponse.success(SuccessCode.GET_SUCCESS, data);
    }
}
