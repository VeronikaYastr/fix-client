package com.veryastr.controller;

import com.veryastr.dto.BaseErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseErrorDto handleInternalException(Exception ex) {
        log.error("Internal exception: " + ex.getMessage(), ex);
        return new BaseErrorDto()
                .setMessage(ex.getMessage())
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setErrors(Collections.singletonList(ex.getMessage()));
    }
}
