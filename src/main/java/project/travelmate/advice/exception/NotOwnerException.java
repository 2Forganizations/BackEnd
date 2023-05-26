package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class NotOwnerException extends CustomException{
    public NotOwnerException() {
        super(ExceptionCodeConst.NOT_OWNER_CODE);
    }

}
