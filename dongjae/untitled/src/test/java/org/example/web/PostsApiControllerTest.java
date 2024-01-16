package org.example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.posts.Posts;
import org.example.domain.posts.PostsRepository;
import org.example.exception.SuccessCode;
import org.example.web.dto.PostsSaveRequestDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    // Mock 없이 사용
    @Test
    public void Posts_등록된다1() throws Exception {

        // given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        // when
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                url, requestDto, String.class
        );

        // then
        JSONParser parser = new JSONParser();
        JSONObject response = (JSONObject) parser.parse(responseEntity.getBody());
        Long id = (Long) response.get("data");

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(id).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    // Mock 라이브러리 사용
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

}