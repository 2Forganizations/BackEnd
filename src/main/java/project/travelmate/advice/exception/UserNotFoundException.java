package project.travelmate.advice.exception;

import static project.travelmate.advice.ExceptionCodeConst.*;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException() {
        super(USER_NOT_FOUND_CODE);
    }

}