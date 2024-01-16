package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러를 JSON 반환하는 컨트롤러로! (@ResponseBody를 각 메소드마다 선언했던 것을 한번에 사용하게 해줌)
public class HelloController {

    @GetMapping("/hello")   // API 생성
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount){
        //@RequestParam: 외부에서 API로 넘긴 파라미터 가져옴. name과 amount는 API를 호출하는 곳에서 넘겨준 값들
        return new HelloResponseDto(name, amount);
    }
}
