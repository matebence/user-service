package com.blesk.userservice.Handler;

import com.blesk.userservice.DTO.Response;
import com.blesk.userservice.Exception.UserServiceException;
import com.blesk.userservice.Value.Messages;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class UserServiceHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Response> handleTypeMismatchException() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.TYPE_MISMATCH_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorObj = new LinkedHashMap<>();
        HashMap<String, String> validations = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validations.put(error.getField(), error.getDefaultMessage());
        }

        errorObj.put("timestamp", new Date());
        errorObj.put("validations", validations);
        errorObj.put("error", true);

        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleUniqueContraintException(DataIntegrityViolationException ex) {
        Map<String, Object> errorObj = new LinkedHashMap<>();
        HashMap<String, String> unique = new HashMap<>();
        ConstraintViolationException exDetail = (ConstraintViolationException) ex.getCause();
        switch (exDetail.getConstraintName()) {
            case "user_id":
                unique.put("userId", Messages.UNIQUE_FIELD_DEFAULT);
            case "user_account_id":
                unique.put("accountId", Messages.USER_ACCOUNT_ID_UNIQUE);
            case "user_tel":
                unique.put("tel", Messages.USER_TEL_UNIQUE);
                break;
            case "place_id":
                unique.put("placeId", Messages.UNIQUE_FIELD_DEFAULT);
                break;
            case "gender_id":
                unique.put("genderId", Messages.UNIQUE_FIELD_DEFAULT);
            case "gender_name":
                unique.put("name", Messages.GENDER_NAME_UNIQUE);
                break;
        }

        errorObj.put("timestamp", new Date());
        errorObj.put("validations", unique);
        errorObj.put("error", true);

        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Response> handleRequestBodyNotFoundException() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.REQUEST_BODY_NOT_FOUND_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserServiceException.class)
    public final ResponseEntity<Response> handleResourcesException(UserServiceException ex) {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), ex.getMessage(), true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Response> handleNotFoundError() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.PAGE_NOT_FOUND_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Response> handlePageNotFoundException() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.PAGE_NOT_FOUND_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Response> handelNullPointerExceptions() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.NULL_POINTER_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public final ResponseEntity<Response> handleDatabaseExceptions() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.SQL_EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Response> handleExceptions() {
        Response errorObj = new Response(new Timestamp(System.currentTimeMillis()).toString(), Messages.EXCEPTION, true);
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}