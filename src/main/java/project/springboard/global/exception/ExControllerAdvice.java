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
    public ModelAndView illegalExHandler(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] {}", e.getMessage());
        return new ModelAndView("error");
    }


    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResult> handleCustomException(final CustomException e) {
        log.error("[handleCustomException] {}", e.getErrorCode());
        ErrorResult errorResult = new ErrorResult("CUSTOM-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ModelAndViewDefiningException.class)
    public ModelAndView ex(ModelAndViewDefiningException e) {
        log.info("[ModelAndViewDefiningException] {}", e.getMessage());
        return new ModelAndView("error");
    }

    @ExceptionHandler(Exception.class)
    protected ModelAndView handleException(final Exception e) {
        log.error("[handleException] {}", e.getMessage());
        return new ModelAndView("error");
    }


}
