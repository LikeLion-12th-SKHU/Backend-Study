package com.rommmu.book.springboot.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rommmu.book.springboot.domain.posts.Posts;
import com.rommmu.book.springboot.domain.posts.PostsRepository;
import com.rommmu.book.springboot.exception.SuccessCode;
import com.rommmu.book.springboot.web.dto.PostsSaveRequestDto;
import com.rommmu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    // Mock없이 사용한다.
//    @Test
//    public void Posts_등록된다1() throws Exception {
//
//        // given
//        String title = "title";
//        String content = "content";
//        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
//                .title(title)
//                .content(content)
//                .author("author")
//                .build();
//
//        String url = "http://localhost:" + port + "/api/v1/posts";
//
//        // when
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
//                url, requestDto, String.class
//        );
//
//        // then
//        JSONParser parser = new JSONParser();
//        JSONObject response = (JSONObject) parser.parse(responseEntity.getBody());
//        Long id = (Long) response.get("data");
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(id).isGreaterThan(0L);
//
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
//        assertThat(all.get(0).getContent()).isEqualTo(content);
//    }

    // Mock 라이브러리를 사용한다
    @Test
    public void Posts_등록된다2() throws Exception {
        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        // when & then
        mvc.perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(requestDto))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value(SuccessCode.POST_SAVE_SUCCESS.getHttpStatusCode()))
                .andExpect(jsonPath("$.message").value(SuccessCode.POST_SAVE_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data").value(1L));
    }

    @Test
    public void Posts_수정된다() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(
                Posts.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build()
        );

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        // when & then
        mvc.perform(
                        put("/api/v1/posts/" + updateId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(SuccessCode.POST_UPDATE_SUCCESS.getHttpStatusCode()))
                .andExpect(jsonPath("$.message").value(SuccessCode.POST_UPDATE_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data").value(updateId));
    }

    @Test
    public void Posts_아이디로_조회된다() throws Exception {
        // given
        Posts savedPosts = postsRepository.save(
                Posts.builder()
                        .title("title")
                        .content("content")
                        .author("author")
                        .build()
        );

        Long id = savedPosts.getId();

        // when & then
        mvc.perform(get("/api/v1/posts/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(SuccessCode.GET_SUCCESS.getHttpStatusCode()))
                .andExpect(jsonPath("$.message").value(SuccessCode.GET_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.title").value("title"))
                .andExpect(jsonPath("$.data.content").value("content"))
                .andExpect(jsonPath("$.data.author").value("author"));
    }

}