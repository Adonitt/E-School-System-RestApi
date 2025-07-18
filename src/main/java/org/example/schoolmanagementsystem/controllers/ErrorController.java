package org.example.schoolmanagementsystem.controllers;

import org.example.schoolmanagementsystem.dtos.ErrorResponse;
import org.example.schoolmanagementsystem.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExistsException(EmailExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<ErrorResponse> handleMissingFieldException(MissingFieldException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MustBe18YOException.class)
    public ResponseEntity<ErrorResponse> handleMustBe18YOException(MustBe18YOException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PersonalNumberAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handlePersonalNumberAlreadyExists(PersonalNumberAlreadyExists ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PersonalNumberLengthException.class)
    public ResponseEntity<ErrorResponse> handlePersonalNumberLengthException(PersonalNumberLengthException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentHasAlreadyANote.class)
    public ResponseEntity<ErrorResponse> handleStudentHasAlreadyANote(StudentHasAlreadyANote ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AttendanceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAttendanceAlreadyExistsException(AttendanceAlreadyExistsException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(message);
        error.setStatus(status.value());
        return new ResponseEntity<>(error, status);
    }
}
