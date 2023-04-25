package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class ContentTypeException extends CustomException {
    public ContentTypeException(ExceptionCodeConst codeConst) {
        super(codeConst);
    }
}
