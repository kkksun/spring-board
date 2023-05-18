package project.springboard.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResult> illegalExHandler(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] {}", e.getMessage());
        ErrorResult errorResult = new ErrorResult("CUSTOM-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResult> handleCustomException(CustomException e) {
        log.error("[handleCustomException] {} = {}", e.getErrorCode(), e.getErrorCode().getMessage());
        ErrorResult errorResult = new ErrorResult("CUSTOM-EX", e.getErrorCode().getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ModelAndViewDefiningException.class)
    public ModelAndView ex(ModelAndViewDefiningException e) {
        log.info("[ModelAndViewDefiningException] {}", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResult> handleException(final Exception e) {
        log.error("[handleException] {}", e.getMessage());
        ErrorResult errorResult = new ErrorResult("GLOBAL-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


}
