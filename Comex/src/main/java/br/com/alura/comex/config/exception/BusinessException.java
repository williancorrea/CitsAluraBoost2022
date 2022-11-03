package br.com.alura.comex.config.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -6511418765787521871L;

    private String messageDescription;

    public BusinessException(String messageDescription) {
        super(messageDescription);
        this.messageDescription = messageDescription;
    }

    public String getMessageDescription() {
        return messageDescription;
    }
}