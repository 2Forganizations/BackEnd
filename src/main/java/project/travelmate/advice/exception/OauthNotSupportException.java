package project.travelmate.advice.exception;

import project.travelmate.advice.ExceptionCodeConst;

public class OauthNotSupportException extends CustomException {

    public OauthNotSupportException(ExceptionCodeConst codeConst) {
        super(codeConst);
    }

}