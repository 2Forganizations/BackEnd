package project.travelmate.advice;

import lombok.Getter;

@Getter
public enum ExceptionCodeConst {


    EXAMPLE_INVALID_CODE("EXAMPLE", "EXAMPLE"),
    USER_NOT_FOUND("USER_NOT_FOUND", "유저를 찾을 수 없습니다."),
    EMPTY_FILE_CODE("EMPTY_FILE", "비어있는 파일이 들어왔습니다."),
    NOT_IMAGE_CONTENT_CODE("NOT_IMAGE_CONTENT_TYPE", "content-type이 이미지 형식이 아닙니다."),
    IO_EXCEPTION_CODE("IO_EXCEPTION", "IO_EXCEPTION");


    private final String code;
    private final String message;

    ExceptionCodeConst(String code, String message) {
        this.code = code;
        this.message = message;
    }

}