package store.beatherb.restapi.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import store.beatherb.restapi.global.exception.dto.response.ExceptionResponse;
import store.beatherb.restapi.global.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(BeatHerbException.class)
    public ResponseEntity<ApiResponse<?>> handleException(BeatHerbException e) {
        int statusCode = e.getStatusCode();
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        ApiResponse<?> ErrorResponse = ApiResponse.fail(e.getStatusCode(),errors);
        return ResponseEntity.status(statusCode).body(ErrorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleException(MethodArgumentNotValidException e){
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        ApiResponse<?> ErrorResponse = ApiResponse.of(HttpStatus.BAD_REQUEST,errors,null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse);
    }
}
