package project.travelmate.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(3)
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public ResponseEntity<String> handle(Exception e) {
        log.info("InvalidFormatException error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
