package br.com.alura.comex.config.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice(value = "br.com.alura")
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


    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(NotFoundException ex, WebRequest request) {

        List<ApiError.Message> messages = new ArrayList<>();
        messages.add(new ApiError.Message(
                        "resource.not-found",
                        ex.getMessageDescription()
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();
        String method = ((ServletWebRequest) request).getRequest().getMethod();

        return handleExceptionInternal(
                ex,
                new ApiError(messages, HttpStatus.NOT_FOUND, uri, method),
                headers,
                HttpStatus.NOT_FOUND,
                request);
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusinessException(BusinessException ex, WebRequest request) {

        List<ApiError.Message> messages = new ArrayList<>();
        messages.add(new ApiError.Message(
                        "business-exception",
                        ex.getMessageDescription()
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String uri = ((ServletWebRequest) request).getRequest().getRequestURI();
        String method = ((ServletWebRequest) request).getRequest().getMethod();

        return handleExceptionInternal(
                ex,
                new ApiError(messages, HttpStatus.BAD_GATEWAY, uri, method),
                headers,
                HttpStatus.BAD_GATEWAY,
                request);
    }

}
