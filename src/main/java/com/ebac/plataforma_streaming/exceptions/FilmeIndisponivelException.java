package com.ebac.plataforma_streaming.exceptions;

public class FilmeIndisponivelException extends RuntimeException {
    public FilmeIndisponivelException(String message) {
        super(message);
    }
}
