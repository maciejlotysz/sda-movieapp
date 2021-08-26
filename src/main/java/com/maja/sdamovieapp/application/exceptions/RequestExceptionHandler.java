package com.maja.sdamovieapp.application.exceptions;

import com.maja.sdamovieapp.application.constants.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String responseMessageHeader = "Message";


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, WebRequest w) {
        String errorMessage = ErrorCode.getInternalCode(ex.getMessage());
        ex.printStackTrace();
        return handleExceptionInternal(ex, ex.getMessage(),
                HttpHeaders.readOnlyHttpHeaders(RequestExceptionHandler.buildHeadersWithMessage(errorMessage)),
                HttpStatus.CONFLICT, w);
    }

    public static MultiValueMap<String, String> buildHeadersWithMessage(String message) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(responseMessageHeader, message);
        return headers;
    }
}