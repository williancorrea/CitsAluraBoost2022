package br.com.alura.comex.config.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiError {
    private final String origin;
    private final String method;
    private final LocalDateTime dataHora;
    private final Status status;
    private List<Message> message = new ArrayList<>();

    public ApiError(List<Message> messages, HttpStatus httpStatus, String origin, String method) {
        this.dataHora = LocalDateTime.now();
        this.origin = origin;
        this.method = method;
        this.message.addAll(messages);
        this.status = new Status(httpStatus.value(), httpStatus.getReasonPhrase());
    }

    @Getter
    public class Status {
        private int code;
        private String description;

        public Status(Integer code, String description) {
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    public static class Message {
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private String key;
        private String description;
        private String detail;

        public Message(String description, String detail) {
            this.key = "";
            this.description = description;
            this.detail = detail;
        }

        public Message(String key, String description, String detail) {
            this.key = key == null ? "" : key;
            this.description = description;
            this.detail = detail;
        }
    }
}
