package com.pagely.common.exception;

import lombok.Getter;

/**
 * 비즈니스 로직 처리 중 발생하는 예외를 표현하는 커스텀 예외 클래스
 *
 * <p>
 * - 서비스 레이어에서 발생하는 도메인/비즈니스 예외를 표현하기 위해 사용합니다. - ErrorCode를 기반으로 예외의 종류와 응답 메시지를 일관되게 관리합니다. - GlobalExceptionHandler에서
 * 해당 예외를 공통 포맷으로 변환하여 응답합니다.
 * </p>
 *
 * <p>
 * 사용 예시:
 * <pre>{@code
 * throw new BusinessException(UserErrorCode.USER_NOT_FOUND);
 * throw new BusinessException(UserErrorCode.INVALID_PASSWORD, "비밀번호가 일치하지 않습니다.");
 * }</pre>
 * </p>
 */
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    /**
     * 기본 생성자
     *
     * <p>
     * ErrorCode에 정의된 기본 메시지를 사용하여 예외를 생성합니다.
     * </p>
     *
     * @param errorCode 공통 에러 코드
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * 상세 메시지를 포함한 생성자
     *
     * <p>
     * ErrorCode의 기본 메시지 대신 커스텀 메시지를 사용하고 싶을 때 사용합니다.
     * </p>
     *
     * @param errorCode     공통 에러 코드
     * @param detailMessage 사용자 정의 상세 메시지
     */
    public BusinessException(ErrorCode errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    /**
     * 원인 예외를 포함한 생성자
     *
     * <p>
     * 하위 예외를 감싸서 전달할 때 사용합니다. (Exception Wrapping)
     * </p>
     *
     * @param errorCode 공통 에러 코드
     * @param cause     원인이 되는 예외
     */
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
