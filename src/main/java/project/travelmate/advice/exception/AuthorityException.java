package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class AuthorityException extends CustomException {

    public AuthorityException() {
        super(ExceptionCodeConst.AUTHORITY_EXCEPTION_CODE);
    }
}
