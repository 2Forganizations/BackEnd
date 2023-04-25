package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class CheckedWrapperException extends RuntimeException {

    private String code;
    private Exception e;

    public CheckedWrapperException(ExceptionCodeConst codeConst, Exception e) {
        super(codeConst.getMessage());
        this.code = codeConst.getCode();
        this.e = e;
    }

    @Override
    public void printStackTrace() {
        e.printStackTrace();
    }

}

