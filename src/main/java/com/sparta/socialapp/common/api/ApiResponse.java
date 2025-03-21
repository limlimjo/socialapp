package com.sparta.socialapp.common.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> {

    private final HttpStatus status;  // HTTP 상태 코드
    private final boolean success;    // 성공 여부
    private final Error error;        // 에러 발생 시 오류 정보
    private final T data;             // 응답 데이터

    public ApiResponse(HttpStatus status, boolean success, String errorCode, String errorMessage, T data) {
        this.status = status;
        this.success = success;
        this.error = (errorCode != null && errorMessage != null) ? new Error(errorCode, errorMessage) : null;
        this.data = data;
    }

    public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, true, null, null, data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String code, String message) {
        return ResponseEntity.status(status).body(new ApiResponse<>(status, false, code, message, null));
    }

    @Getter
    public static class Error {
        private final String errorCode;
        private final String errorMessage;

        public Error(String errorCode, String errorMessage) {
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
    }
}