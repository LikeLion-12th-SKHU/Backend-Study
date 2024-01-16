package com.jojoldu.book.springboot.web.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);
        // assertj라는 테스트 검증 메소드, 검증하고 싶은 대상을 메소드 인자로 받음
        assertThat(dto.getAmount()).isEqualTo(amount);
        // assertj의 동등 비교 메소드. assertThat에 있는 값과 isEqualTo의 값을 비교해서 같을 때만 성공
        // 필자는 Junit의 기본 asserThat이 아닌 assertj의 것을 사용함.
    }
    @RunWith(SpringRunner.class)
    @WebMvcTest
    public class HelloControllerTest{
        @Autowired
        private MockMvc mvc;

        @Test
        public void hello가_리턴된다() throws Exception {
            String hello = "hello";

            mvc.perform(get("/hello"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(hello));



        }
    }
}
