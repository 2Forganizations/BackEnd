package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(ExceptionCodeConst codeConst) {
        super(codeConst);
    }

}