package com.arpiatecnologia.exception.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
@RequiredArgsConstructor
public class ApiErrorResponse {

    private final List<ApiError> errors;

    public static ApiErrorResponse of(List<ApiError> errors) {
        return new ApiErrorResponse(errors);
    }

    public static ApiErrorResponse of(ApiError error) {
        return ApiErrorResponse.of(Collections.singletonList(error));
    }

    @JsonAutoDetect(fieldVisibility = ANY)
    @RequiredArgsConstructor
    public static class ApiError {
        private final String message;
        private final String msgDev;
    }
}
