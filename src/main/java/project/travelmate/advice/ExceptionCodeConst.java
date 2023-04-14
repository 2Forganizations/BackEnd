package project.travelmate.advice;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {

    EXAMPLE_INVALID_CODE("EXAMPLE", "EXAMPLE"),
    USER_NOT_FOUND("USER_NOT_FOUND", "유저를 찾을 수 없습니다.");

    private final String code;
    private final String message;

    ExceptionCodeConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

}