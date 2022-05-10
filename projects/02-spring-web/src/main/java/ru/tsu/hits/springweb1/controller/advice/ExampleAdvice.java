package ru.tsu.hits.springweb1.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tsu.hits.springweb1.dto.ErrorDto;
import ru.tsu.hits.springweb1.exception.NotFoundException;

import java.util.Date;

//@ControllerAdvice
public class ExampleAdvice extends ResponseEntityExceptionHandler {

    /**
     * Обработчик NotFound
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFound(NotFoundException ex) {
        var body = ErrorDto.create(new Date(), HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Дефолтный обработчик
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleNotFound(Exception ex) {
        var body = ErrorDto.create(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Внутренняя ошибка сервера");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

}
