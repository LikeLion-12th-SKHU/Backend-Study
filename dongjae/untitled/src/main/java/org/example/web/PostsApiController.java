package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.BaseResponse;
import org.example.exception.SuccessCode;
import org.example.service.PostsService;
import org.example.web.dto.PostsResponseDto;
import org.example.web.dto.PostsSaveRequestDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED) // 기본 상태 코드 201로 전환
    public BaseResponse<Long> save(@RequestBody @Valid PostsSaveRequestDto requestDto) {
        final Long data = postsService.save(requestDto);
        return BaseResponse.success(SuccessCode.POST_SAVE_SUCCESS, data);
    }

    @PutMapping("/api/v1/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> update(@PathVariable Long id, @RequestBody @Valid PostsUpdateRequestDto requestDto) {
        final Long data = postsService.update(id, requestDto);
        return BaseResponse.success(SuccessCode.POST_UPDATE_SUCCESS, data);
    }

    @GetMapping("/api/v1/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PostsResponseDto> findById(@PathVariable Long id) {
        final PostsResponseDto data = postsService.findById(id);
        return BaseResponse.success(SuccessCode.GET_SUCCESS, data);
    }

    /*
    1. 기본 생성자로 객체를 생성합니다.
    2. 요청 매개변수의 이름과 객체의 필드 이름이 일치하는 경우, 해당 값을 객체의 필드에 자동으로 바인딩합니다.
     */
    @GetMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Page<PostsResponseDto>> findAll(
            @RequestParam(defaultValue = "0", required = false) int pg,
            @RequestParam(defaultValue = "5", required = false) int sz
    ) {
        final Page<PostsResponseDto> data = postsService.findAll(pg, sz);
        return BaseResponse.success(SuccessCode.GET_SUCCESS, data);
    }

}
