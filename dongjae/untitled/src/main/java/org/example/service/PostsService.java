package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.posts.Posts;
import org.example.domain.posts.PostsRepository;
import org.example.exception.ErrorCode;
import org.example.exception.model.NotFoundException;
import org.example.web.dto.PostsResponseDto;
import org.example.web.dto.PostsSaveRequestDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POSTS_NOT_FOUND_EXCEPTION, ErrorCode.POSTS_NOT_FOUND_EXCEPTION.getMessage() + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POSTS_NOT_FOUND_EXCEPTION, ErrorCode.POSTS_NOT_FOUND_EXCEPTION.getMessage() + id));

        return new PostsResponseDto(entity);
    }

}
