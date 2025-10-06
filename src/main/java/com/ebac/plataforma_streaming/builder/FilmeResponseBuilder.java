package com.ebac.plataforma_streaming.builder;

import com.ebac.plataforma_streaming.enums.Plano;
import com.ebac.plataforma_streaming.response.FilmeComPlanoResponse;
import com.ebac.plataforma_streaming.response.FilmeResponse;

public class FilmeResponseBuilder {

    private String titulo;
    private String plano;

    public static FilmeResponseBuilder create(){
        return new FilmeResponseBuilder();
    }

    public FilmeResponseBuilder titulo(String titulo){
        this.titulo = titulo;
        return this;
    }

    public FilmeResponseBuilder plano(String plano){
        this.plano = plano;
        return this;
    }

    public FilmeResponse build(Plano planoUsuarioAssinante){
        if(Plano.PREMIUM.equals(planoUsuarioAssinante)) {
            FilmeResponse response = new FilmeResponse();
            response.setTitulo(titulo);
            return response;
        } else {
            FilmeComPlanoResponse response = new FilmeComPlanoResponse();
            response.setTitulo(titulo);
            response.setPlano(plano);
            return response;
        }
    }
}
