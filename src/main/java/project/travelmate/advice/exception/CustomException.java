package project.travelmate.advice.exception;

import lombok.Getter;
import project.travelmate.advice.ExceptionCodeConst;

@Getter
public class CustomException extends RuntimeException {

    private String code;
    private Exception e;

    public CustomException(ExceptionCodeConst codeConst, Exception e) {
        super(codeConst.getMessage());
        this.code = codeConst.getCode();
        this.e = e;
    }

    public CustomException(ExceptionCodeConst codeConst) {
        super(codeConst.getMessage());
        this.code = codeConst.getCode();
    }


    @Override
    public void printStackTrace() {
        e.printStackTrace();
    }


}