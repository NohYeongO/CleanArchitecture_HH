package io.hhplus.hh_cleanarchitecture.common;


import io.hhplus.hh_cleanarchitecture.common.exception.InstructorNotFoundException;
import io.hhplus.hh_cleanarchitecture.common.exception.LectureException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorMessage("500", "에러가 발생했습니다."));
    }

    @ExceptionHandler(value = LectureException.class)
    public ResponseEntity<ErrorMessage> handleLectureException(LectureException e) {
        return ResponseEntity.status(e.getCode()).body(new ErrorMessage(String.valueOf(e.getCode()), e.getMessage()));
    }

    @ExceptionHandler(value = InstructorNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleInstructorNotFoundException(InstructorNotFoundException e) {
        return ResponseEntity.status(404).body(new ErrorMessage("404", e.getMessage()));
    }
}
