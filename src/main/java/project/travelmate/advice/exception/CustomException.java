package project.travelmate.advice.exception;

import lombok.Getter;
import project.travelmate.advice.ExceptionCodeConst;

@Getter
public class CustomException extends RuntimeException {

    private String code;
    private Exception e;

    public CustomException(ExceptionCodeConst codeConst) {
        super(codeConst.getMessage());
        this.code = codeConst.getCode();
    }
}