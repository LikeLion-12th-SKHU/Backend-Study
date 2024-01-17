package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.BaseResponse;
import org.example.exception.SuccessCode;
import org.example.service.PostsService;
import org.example.web.dto.PostsResponseDto;
import org.example.web.dto.PostsSaveRequestDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED) // 기본 상태 코드 201로 전환
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
