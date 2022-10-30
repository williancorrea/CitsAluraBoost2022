package br.com.alura.comex.config.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6511418765787521871L;

    private String messageDescription;

    public NotFoundException(String messageDescription) {
        super(messageDescription);
        this.messageDescription = messageDescription;
    }

    public String getMessageDescription() {
        return messageDescription;
    }
}