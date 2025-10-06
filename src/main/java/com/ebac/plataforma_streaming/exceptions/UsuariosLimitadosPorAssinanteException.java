package com.ebac.plataforma_streaming.exceptions;

public class UsuariosLimitadosPorAssinanteException extends RuntimeException {
    public UsuariosLimitadosPorAssinanteException(String message){
        super(message);
    }
}
