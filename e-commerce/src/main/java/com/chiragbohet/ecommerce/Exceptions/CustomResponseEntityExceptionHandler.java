package com.chiragbohet.ecommerce.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    // Default exception handler
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponseFormat exceptionResponseFormat =
                new ExceptionResponseFormat(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // UserNotFoundException exception handler
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponseFormat exceptionResponseFormat =
                new ExceptionResponseFormat(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.NOT_FOUND);
    }

    // UserNotFoundException exception handler
    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> ResourceNotFoundException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponseFormat exceptionResponseFormat =
                new ExceptionResponseFormat(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.NOT_FOUND);
    }

    // UserAlreadyExistsException exception handler
    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<Object> handleUserAlreadyExistsException(Exception ex, WebRequest request) throws Exception {
        ExceptionResponseFormat exceptionResponseFormat =
                new ExceptionResponseFormat(new Date(),
                        ex.getMessage(),
                        request.getDescription(false));

        // ref : https://stackoverflow.com/questions/3825990/http-response-code-for-post-when-resource-already-exists
        return new ResponseEntity(exceptionResponseFormat, HttpStatus.CONFLICT);
    }

    // For validation related exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        // ref : https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
        List<String> validationErrors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ExceptionResponseFormat exceptionResponseFormat =
                new ExceptionResponseFormat(new Date(),
                        "Validation error, please check the details for more info.",
                        validationErrors.toString());

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.BAD_REQUEST);
    }

}
