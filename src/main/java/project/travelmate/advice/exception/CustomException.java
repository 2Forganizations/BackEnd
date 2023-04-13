package project.travelmate.advice.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private String code;
    private Exception e;

    public CustomException(String message, String code, Exception e) {
        super(message);
        this.code = code;
        this.e = e;
    }

    @Override
    public void printStackTrace() {
        e.printStackTrace();
    }


}