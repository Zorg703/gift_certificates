package com.epam.esm.oauth2.exceptionhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * The type Rest exception handler.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);
    private static final String ENTITY_IN_DB_NOT_FOUND = "not_found";
    private static final String INCORRECT_INPUT_DATA = "incorrect";
    private static final String PROBLEMS_WITH_DATA_BASE = "data_access";
    private static final String OBJECT_ALREADY_EXIST_IN_DB = "exist";
    private static final String BAD_REQUEST = "bad_request";
    private static final String RESOURCE_BUNDLE_NAME = "localization";
    private static final String PAGE_NOT_FOUND = "page_not_found";
    private static final String INCORRECT_REQUEST_METHOD = "request_method";
    private static final String EXCEPTION = "exception";

    /**
     * Handle data access response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccess(EmptyResultDataAccessException e, WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,ENTITY_IN_DB_NOT_FOUND);
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    /**
     * Handle data access exception response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e, WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,PROBLEMS_WITH_DATA_BASE);
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    /**
     * Handle data integrity exception response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value=DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityException(DataAccessException e, WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,OBJECT_ALREADY_EXIST_IN_DB);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle no such element exception response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,ENTITY_IN_DB_NOT_FOUND);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle  duplicate key exceptions entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handlePSQL(DuplicateKeyException e, WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,OBJECT_ALREADY_EXIST_IN_DB);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle method argument type mismatch exception response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,BAD_REQUEST);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle method argument type method not supported exception response entity.
     *
     * @param e the e
     * @param headers the headers
     * @param status the status
     * @param request the requst
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,INCORRECT_REQUEST_METHOD);
        return new ResponseEntity<>(errorResponse,HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     *Handle method argument type exception response entity.
     *
     * @param e       the e
     * @param request the request
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse= getErrorResponse(e,request,EXCEPTION);
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse= getErrorResponse(ex,request,INCORRECT_INPUT_DATA);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle method argument type no handle found exception response entity.
     *
     * @param e the e
     * @param headers the headers
     * @param status the status
     * @param request the requst
     * @return the response entity
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse=getErrorResponse(e,request,PAGE_NOT_FOUND);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get parametr from handle
     *
     * @param e
     * @param request
     * @param msg
     * @return the error response entity
     */

    private ErrorResponse getErrorResponse(Exception e,WebRequest request,String msg){
        Locale locale=request.getLocale();
        ResourceBundle resourceBundle=ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME,locale);
        LOG.debug(e.getMessage());
        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setMessage(resourceBundle.getString(msg));
        return errorResponse;
    }
}
