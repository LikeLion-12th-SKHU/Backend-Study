package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
// 테스트 진행 시 JUnit에 내장된 실행자 외에 다른 실행자 실행
// SpringRunner 이용! = 스프링 실행자!!!(연결자 역할)
@WebMvcTest(controllers = HelloController.class)
// Web에 집중 =>
// @Controller, @ControllerAdvice 사용 가능, @Service, @Component, @Repository 사용 불가
// 어차피 여기서는 컨트롤러만 사용할 거라 선언!!!!
public class HelloControllerTest {
    @Autowired  //빈 받기
    private MockMvc mvc;    // 웹 API 테스트시 사용(테스트 시작점) - http get, post 등에 대한 api테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";
        //얘는 왜 바꾸래
        mvc.perform(get("/hello")) // MockMvc 통해 /hello 주소로 get요청
                .andExpect(status().isOk()) // 200 검증
                .andExpect(content().string(hello));  

                //응답 본문 내용 검증
                //Controller에서 "hello"를 리턴하기 때문에 이 값 검증

    }
}
