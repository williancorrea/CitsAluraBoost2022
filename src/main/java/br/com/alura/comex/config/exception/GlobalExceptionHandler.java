package br.com.alura.comex.config.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(@Lazy MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();
        String method = ((ServletWebRequest) request).getRequest().getMethod();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ApiError.Message> messages = new ArrayList<>();
        fieldErrors.forEach(e -> messages.add(new ApiError.Message(e.getField(), messageSource.getMessage(e, LocaleContextHolder.getLocale()))));

        return handleExceptionInternal(
                ex,
                new ApiError(messages, HttpStatus.BAD_REQUEST, uri, method),
                headers,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handlerNotFoundException(NotFoundException ex, HttpHeaders headers, WebRequest request) {

        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();
        String method = ((ServletWebRequest) request).getRequest().getMethod();

        List<ApiError.Message> messages = new ArrayList<>();
        messages.add(new ApiError.Message(
                        "resource.not-found",
                        messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale())
                )
        );

        return handleExceptionInternal(
                ex,
                new ApiError(messages, HttpStatus.NOT_FOUND, uri, method),
                headers,
                HttpStatus.NOT_FOUND,
                request);
    }

}
