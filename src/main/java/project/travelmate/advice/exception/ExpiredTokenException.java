package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class ExpiredTokenException extends CustomException {

    public ExpiredTokenException(ExceptionCodeConst codeConst) {
        super(codeConst);
    }

}