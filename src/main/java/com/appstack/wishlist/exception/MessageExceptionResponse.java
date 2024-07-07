package com.appstack.wishlist.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MessageExceptionResponse {
    private String message;
    private String httpStatusReason;
    private Integer httpStatusCode;
}
