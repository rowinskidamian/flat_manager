package pl.damianrowinski.flat_manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.exceptions.FrobiddenAccessException;
import pl.damianrowinski.flat_manager.exceptions.ObjectInRelationshipException;

@ControllerAdvice
@Slf4j
public class ExceptionsHandlerController {

    private static final String ERROR_PAGE_ATTRIBUTE = "errorMessage";
    private static final String ERROR_PAGE_VIEW = "/error/exception";

    @ExceptionHandler(ElementNotFoundException.class)
    public String handleConflict(Exception e, Model model){
        log.error("Raised exception ElementNotFoundException.");
        model.addAttribute(ERROR_PAGE_ATTRIBUTE, e.getMessage());
        return ERROR_PAGE_VIEW;
    }

    @ExceptionHandler(FrobiddenAccessException.class)
    public String handleForbiddenAccess(Exception e, Model model){
        log.error("Raised exception ForbiddenAccessException");
        model.addAttribute(ERROR_PAGE_ATTRIBUTE, e.getMessage());
        return ERROR_PAGE_VIEW;
    }

    @ExceptionHandler(ObjectInRelationshipException.class)
    public String handleRelationshipException(Exception e, Model model){
        log.error("Raised exception ObjectInRelationshipExcetion");
        model.addAttribute(ERROR_PAGE_ATTRIBUTE, e.getMessage());
        return ERROR_PAGE_VIEW;
    }

}
