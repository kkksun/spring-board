package project.springboard.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

@Slf4j
@ControllerAdvice
public class ExControllerAdvice{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalExHandler(IllegalArgumentException e) {
        log.error("[IllegalArgumentException] {}", e.getMessage());
        return new ModelAndView("error");
    }


    @ExceptionHandler(CustomException.class)
    protected ModelAndView handleCustomException(final CustomException e) {
        log.error("[handleCustomException] {}", e.getErrorCode());
        return new ModelAndView("error");
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
