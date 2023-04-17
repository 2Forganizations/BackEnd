package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class CheckedWrapperException extends CustomException {
    public CheckedWrapperException(ExceptionCodeConst codeConst, Exception e) {
        super(codeConst, e);
    }

    public CheckedWrapperException(ExceptionCodeConst codeConst) {
        super(codeConst);
    }
}
