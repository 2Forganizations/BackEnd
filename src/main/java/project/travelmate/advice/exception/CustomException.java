package project.travelmate.advice.exception;

import lombok.Getter;
import project.travelmate.advice.ExceptionCodeConst;

@Getter
public class CustomException extends RuntimeException {

    private final String code;

    public CustomException(ExceptionCodeConst codeConst) {
        super(codeConst.getMessage());
        this.code = codeConst.getCode();
    }

}