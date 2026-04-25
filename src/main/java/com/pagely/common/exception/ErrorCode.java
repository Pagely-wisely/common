package com.pagely.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    // ErrorCode -> Enum Name (예시: BAD_REQUEST, USER_NOT_FOUND)
    String getCode();

    // 사용자에게 노출할 메시지
    String getMessage();

    // HTTP 상태 코드
    HttpStatus getHttpStatus();
}
