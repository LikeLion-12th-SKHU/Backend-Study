package com.rommmu.book.springboot.service;

import com.rommmu.book.springboot.domain.posts.Posts;
import com.rommmu.book.springboot.domain.posts.PostsRepository;
import com.rommmu.book.springboot.exception.ErrorCode;
import com.rommmu.book.springboot.exception.model.NotFoundException;
import com.rommmu.book.springboot.web.dto.PostsListResponseDto;
import com.rommmu.book.springboot.web.dto.PostsResponseDto;
import com.rommmu.book.springboot.web.dto.PostsSaveRequestDto;
import com.rommmu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POSTS_NOT_FOUND_EXCEPTION, ErrorCode.POSTS_NOT_FOUND_EXCEPTION.getMessage() + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
