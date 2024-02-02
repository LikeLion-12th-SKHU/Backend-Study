package org.example.web;

import lombok.RequiredArgsConstructor;
import org.example.common.dto.BaseResponse;
import org.example.config.auth.LoginUser;
import org.example.config.auth.dto.SessionUser;
import org.example.exception.SuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class IndexController {

    @GetMapping("/")
    public BaseResponse<String> index(@LoginUser SessionUser user) {
        String data;
        if (user != null) {
            data = user.getName() + "님 환영합니다.";
        } else {
            data = "로그인을 진행해주세요.";
        }
        return BaseResponse.success(SuccessCode.GET_SUCCESS, data);
    }

}
