package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class EmptyFileException extends CustomException {

    public EmptyFileException(ExceptionCodeConst codeConst) {
        super(codeConst);
    }
}
