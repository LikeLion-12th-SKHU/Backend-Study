package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.BaseResponse;
import org.example.exception.SuccessCode;
import org.example.service.PostsService;
import org.example.web.dto.PostsSaveRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<Long> save(@RequestBody PostsSaveRequestDto requestDto) {
        final Long data = postsService.save(requestDto);
        return BaseResponse.success(SuccessCode.POST_SAVE_SUCCESS, data);
    }

}
