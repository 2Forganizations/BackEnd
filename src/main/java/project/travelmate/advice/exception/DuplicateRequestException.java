package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class DuplicateRequestException extends CustomException {
    public DuplicateRequestException() {
        super(ExceptionCodeConst.DUPLICATE_REQUEST_EXCEPTION_CODE);
    }
}
