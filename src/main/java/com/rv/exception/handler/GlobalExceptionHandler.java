package com.rv.exception.handler;

import com.rv.exception.DuplicateDataException;
import com.rv.exception.InvalidInputException;
import com.rv.exception.NoRecordsFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<APIErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {

        APIErrorResponse APIErrorResponse = new APIErrorResponse(request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(APIErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<APIErrorResponse> handleInvalidRequestExceptions(InvalidInputException ex, WebRequest request) {

        APIErrorResponse APIErrorResponse = new APIErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(APIErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateDataException.class)
    public final ResponseEntity<APIErrorResponse> handleDuplicateDataException(DuplicateDataException ex, WebRequest request) {

        APIErrorResponse APIErrorResponse = new APIErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(APIErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoRecordsFoundException.class)
    public final ResponseEntity<APIErrorResponse> handleInvalidRequestExceptions(NoRecordsFoundException ex, WebRequest request) {

        APIErrorResponse APIErrorResponse = new APIErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(APIErrorResponse, HttpStatus.NOT_FOUND);
    }

}