package com.appstack.wishlist.exception;

import com.appstack.wishlist.config.MDCKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<MessageExceptionResponse> handleItemNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<MessageExceptionResponse> handlePreconditionFailedException(PreconditionFailedException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(buildExceptionResponse(ex.getMessage(), HttpStatus.PRECONDITION_FAILED));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageExceptionResponse> handleItemNotFoundException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildExceptionResponse(extractDefaultMessage(ex), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageExceptionResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(buildExceptionResponse(ErrorMessage.GENERIC_ERROR.getMessage(), HttpStatus.PRECONDITION_FAILED));
    }

    private MessageExceptionResponse buildExceptionResponse(final String message, final HttpStatus httpStatus) {
        String requestId = MDC.get(MDCKeys.REQUEST_ID.getKey());
        logger.error("message: {}. Request ID: {}. Http Status: {}", message, requestId, httpStatus);
        return MessageExceptionResponse.builder()
                .message(message)
                .httpStatusReason(httpStatus.getReasonPhrase())
                .httpStatusCode(httpStatus.value())
                .build();
    }

    private String extractDefaultMessage(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }
}
