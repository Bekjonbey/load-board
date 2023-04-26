package com.example.transaction2.response;

import com.example.transaction2.error.ErrorData;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ApiResult<E> {
    private boolean success;

    private String message;

    private E data;

    private List<ErrorData> errors;

    private ApiResult(String message, E data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }
    private ApiResult(E data, Boolean success) {
        this.data = data;
        this.success = success;
    }
    private ApiResult(String errorMsg, Integer errorCode) {
        this.success = false;
        this.errors = Collections.singletonList(new ErrorData(errorMsg, errorCode));
    }
    private ApiResult(List<ErrorData> errors) {
        this.errors = errors;
    }
    private ApiResult() {
        this.success = true;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiResult)) return false;
        ApiResult<?> that = (ApiResult<?>) o;
        return this.isSuccess() == that.isSuccess() &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                Objects.equals(this.getData(), that.getData()) &&
                Objects.equals(this.getErrors(), that.getErrors());
    }
    public static <T> ApiResult<T> successResponse(String message, T data) {
        return new ApiResult<>(message, data);
    }


    public static <E> ApiResult<E> successResponse(E data) {
        return new ApiResult<>(data, true);
    }
    public static <T> ApiResult<T> successResponse() {
        return new ApiResult<>();
    }
    public static ApiResult<List<ErrorData>> failResponse(List<ErrorData> errors) {
        return new ApiResult<>(errors);
    }

    public static ApiResult<List<ErrorData>> failResponse(String msg, Integer code) {
        List<ErrorData> errorDataList = List.of(new ErrorData(msg, code));
        return failResponse(errorDataList);
    }
    public static <T> ApiResult<T> failResponse() {

        return new ApiResult<>();
    }
    public static ApiResult<ErrorData> errorResponse(String errorMsg, Integer errorCode) {
        return new ApiResult<>(errorMsg, errorCode);
    }

    public static ApiResult<ErrorData> errorResponse(List<ErrorData> errors) {
        return new ApiResult<>(errors);
    }


}
