package com.arpiatecnologia.exception;

import com.arpiatecnologia.exception.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.arpiatecnologia.exception.model.ApiErrorResponse.ApiError;

@Component
@RestControllerAdvice
public class ApiExceptionHandler {

    private List<ApiError> toApiErrors(BindingResult bindingResult) {
        final List<ApiError> apiErrors = new ArrayList<>();

        for (ObjectError objectError : bindingResult.getAllErrors()) {
            final String message = objectError.getDefaultMessage();
            final String msgDev = "Na Classe " + objectError.getObjectName() +
                    "error: "+ objectError.getDefaultMessage();

            apiErrors.add(new ApiError(message, msgDev));
        }

        return apiErrors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ApiError> allErros = this.toApiErrors(bindingResult);

        return ApiErrorResponse.of(allErros);
    }
}
