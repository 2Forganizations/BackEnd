package project.travelmate.advice;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    EXAMPLE_INVALID_CODE("EXAMPLE", "EXAMPLE"),
    USER_NOT_FOUND_CODE("USER_NOT_FOUND", "유저를 찾을 수 없습니다."),
    PLAN_NOT_FOUND_CODE("PLAN_NOT_FOUND", "플랜을 찾을 수 없습니다."),
    OAUTH_NOT_SUPPORT_CODE("OAUTH_NOT_SUPPORT", "지원되지 않는 인증입니다."),
    EXPIRED_ACCESS_TOKEN_CODE("EXPIRED_ACCESS_TOKEN", "기한이 만료된 토큰입니다."),
    EMPTY_FILE_CODE("EMPTY_FILE", "비어있는 파일이 들어왔습니다."),
    NOT_IMAGE_CONTENT_CODE("NOT_IMAGE_CONTENT_TYPE", "content-type이 이미지 형식이 아닙니다."),
    IO_EXCEPTION_CODE("IO_EXCEPTION", "IO_EXCEPTION"),
    NOT_OWNER_CODE("AUTHORITY_EXCEPTION", "접근 권한이 부족합니다.");

    private final String code;
    private final String message;

    ExceptionCodeConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

}