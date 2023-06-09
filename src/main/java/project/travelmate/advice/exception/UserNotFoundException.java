package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super(ExceptionCodeConst.USER_NOT_FOUND_CODE);
    }

    public UserNotFoundException(ExceptionCodeConst codeConst) {
        super(codeConst);
    }

}