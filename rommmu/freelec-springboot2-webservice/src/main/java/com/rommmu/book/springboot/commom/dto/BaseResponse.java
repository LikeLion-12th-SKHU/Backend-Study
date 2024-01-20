package com.rommmu.book.springboot.commom.dto;

import com.rommmu.book.springboot.exception.ErrorCode;
import com.rommmu.book.springboot.exception.SuccessCode;
import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Builder
public class BaseResponse<T> {
    // 제네릭 타입으로 선언한다.

    private final int code;
    private final String message;
    private T data; // 결정되지 않은 타입의 필드를 선언


    // 전달할 data없이 단순히 상태코드와 메시지만 전달할 경우
    public static BaseResponse success(SuccessCode success) {
        return new BaseResponse<>(success.getHttpStatusCode(), success.getMessage());
    }


    // 제네릭 메소드
    // 리턴타입은 T를 내용물(data) 로 갖는 BaseResponse 객체
    public static <T> BaseResponse<T> success(SuccessCode success, T data) {
        return new BaseResponse<T>(success.getHttpStatusCode(), success.getMessage(), data);
    }

    // ErrorCode의 기본정보를 이용하고 싶을 경우
    public static BaseResponse error(ErrorCode error) {
        return new BaseResponse<>(error.getHttpStatusCode(), error.getMessage());
    }

    // Code는 기존 ErrorCode의 정보를 이용하되, 전달 메시지를 커스텀하고 싶을 경우
    public static BaseResponse error(ErrorCode error, String message) {
        return new BaseResponse<>(error.getHttpStatusCode(), message);
    }

}

