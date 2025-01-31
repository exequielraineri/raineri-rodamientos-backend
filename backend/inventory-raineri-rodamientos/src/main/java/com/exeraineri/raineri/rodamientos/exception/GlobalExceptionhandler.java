package com.exeraineri.raineri.rodamientos.exception;

import com.exeraineri.raineri.rodamientos.payload.ApiResponse;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpHeaderParser;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionhandler {


    // 🔹 Manejo de excepción personalizada
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(
                        false,
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        null));
    }

    // 🔹 Manejo de error de integridad de base de datos (duplicados, claves foráneas, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Error de integridad de datos: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        false,
                        HttpStatus.BAD_REQUEST.value(),
                        "Datos inválidos o duplicados",
                        null));
    }

    // 🔹 Manejo de error cuando un argumento no tiene el tipo correcto
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("Tipo de dato incorrecto: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(
                        false,
                        HttpStatus.BAD_REQUEST.value(),
                        "Parámetro incorrecto",
                        null));
    }

    // 🔹 Manejo general para excepciones no controladas
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(
                        false,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Error interno del servidor",
                        ex));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return new ResponseEntity<>(
                new ApiResponse<>(
                        false,
                        HttpStatus.BAD_REQUEST.value(),
                        "Error de validacion",
                        result.getFieldErrors()
                                .stream()
                                .map(FieldError::getDefaultMessage)
                                .collect(Collectors.toList())
                        ),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handlerNoResourceFoundException(
            NoResourceFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        false,
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                       null
                ),
                HttpStatus.NOT_FOUND);
    }
}
