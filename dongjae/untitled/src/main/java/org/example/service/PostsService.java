package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.posts.Posts;
import org.example.domain.posts.PostsRepository;
import org.example.exception.ErrorCode;
import org.example.exception.model.NotFoundException;
import org.example.web.dto.PostsResponseDto;
import org.example.web.dto.PostsSaveRequestDto;
import org.example.web.dto.PostsUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /**
     *
     * content: 배열로 구성된 객체 목록. 각 객체는 학생의 정보를 나타냅니다.
     *
     * <br><br/>pageable: 페이지에 대한 정보를 포함하는 객체.
     *
     * <br><br/>pageNumber: 현재 페이지의 번호.
     * <br><br/>pageSize: 한 페이지에 표시되는 항목 수.
     * <br><br/>sort: 정렬에 대한 정보를 포함하는 객체.
     * <br><br/>empty: 정렬이 비어있는지 여부.
     * <br><br/>unsorted: 정렬되지 않은 상태인지 여부.
     * <br><br/>sorted: 정렬된 상태인지 여부.
     * <br><br/>offset: 현재 페이지의 첫 번째 항목이 전체 목록에서 어디에 위치하는지의 인덱스.
     * <br><br/>paged: 페이징된 상태인지 여부.
     * <br><br/>unpaged: 페이징되지 않은 상태인지 여부.
     * <br><br/>last: 마지막 페이지 여부.
     *
     * <br><br/>totalPages: 전체 페이지 수.
     *
     * <br><br/>totalElements: 전체 항목 수.
     *
     * <br><br/>first: 첫 페이지 여부.
     *
     * <br><br/>size: 현재 페이지의 항목 수.
     *
     * <br><br/>number: 현재 페이지의 번호.
     *
     * <br><br/>sort: 정렬에 대한 정보를 포함하는 객체 (위의 sort와 동일한 구조).
     *
     * <br><br/>numberOfElements: 현재 페이지의 실제 항목 수.
     *
     * <br><br/>empty: 목록이 비어있는지 여부.
     */
    public Page<PostsResponseDto> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<Posts> postsPage = postsRepository.findAll(pageRequest);
        return postsPage.map(PostsResponseDto::new);
    }

}
