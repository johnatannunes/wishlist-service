package com.appstack.wishlist.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<MessageExceptionResponse> handleWishlistNotFoundException(WishlistNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<MessageExceptionResponse> handleItemNotFoundException(ItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<MessageExceptionResponse> handlePreconditionFailedException(PreconditionFailedException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(buildExceptionResponse(ex.getMessage(), HttpStatus.PRECONDITION_FAILED));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageExceptionResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body(buildExceptionResponse("Something went wrong. Please try again or " +
                        "contact support if the issue persists.", HttpStatus.PRECONDITION_FAILED));
    }

    private MessageExceptionResponse buildExceptionResponse(final String message, final HttpStatus httpStatus){
        String requestId = MDC.get("requestId");
        logger.error("message: {}. Request ID: {}. Http Status: {}", message, requestId, httpStatus);
        return MessageExceptionResponse.builder()
                .message(message)
                .httpStatusReason(httpStatus.getReasonPhrase())
                .httpStatusCode(httpStatus.value())
                .build();
    }
}
