package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.common.dto.BaseResponse;
import com.jojoldu.book.springboot.exception.SuccessCode;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED) //기본 상태 코드 201로 전환
    public BaseResponse<Long> save(@RequestBody PostsSaveRequestDto requestDto){
        final Long data = postsService.save(requestDto);
        return BaseResponse.success(SuccessCode.POST_SAVE_SUCCESS, data);
    }

    @PutMapping("/api/v1/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Long> update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        final Long data = postsService.update(id, requestDto);
        return BaseResponse.success(SuccessCode.POST_UPDATE_SUCCESS, data);
    }
    @GetMapping("/api/v1/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<PostsResponseDto> findById(@PathVariable Long id){
        final PostsResponseDto data = postsService.findById(id);
        return BaseResponse.success(SuccessCode.GET_SUCCESS, data);
    }
//    @PostMapping("/api/v1/posts")
//    public Long save(@RequestBody PostsSaveRequestDto requestDto){
//        return postsService.save(requestDto);
//    }
//
//    @PutMapping("/api/v1/posts/{id}")
//    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
//        return postsService.update(id, requestDto);
//    }
//    @GetMapping("/api/v1/posts/{id}")
//    public PostsResponseDto findById(@PathVariable Long id){
//        return postsService.findById(id);
//    }
}
