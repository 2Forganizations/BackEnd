package project.travelmate.advice;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    EXAMPLE_INVALID_CODE("EXAMPLE", "EXAMPLE"),
    USER_NOT_FOUND_CODE("USER_NOT_FOUND", "유저를 찾을 수 없습니다."),
    OAUTH_NOT_SUPPORT_CODE("OAUTH_NOT_SUPPORT", "지원되지 않는 인증입니다."),
    EXPIRED_ACCESS_TOKEN_CODE("EXPIRED_ACCESS_TOKEN", "기한이 만료된 토큰입니다.");

    private final String code;
    private final String message;

    ExceptionCodeConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

}