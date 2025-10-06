package com.ebac.plataforma_streaming.builder;

import com.ebac.plataforma_streaming.response.FilmeHistoricoResponse;

public class FilmeHistoricoResponseBuilder {

    private String titulo;
    private Integer ano;
    private String classificacao;
    private static final String ANOS = " anos";

    public static FilmeHistoricoResponseBuilder create(){
        return new FilmeHistoricoResponseBuilder();
    }

    public FilmeHistoricoResponseBuilder titulo(String titulo){
        this.titulo = titulo;
        return this;
    }

    public FilmeHistoricoResponseBuilder ano(Integer ano){
        this.ano = ano;
        return this;
    }

    public FilmeHistoricoResponseBuilder classificacao(Integer ano){
        this.classificacao = ano + ANOS;
        return this;
    }

    public FilmeHistoricoResponse build(){
        FilmeHistoricoResponse response = new FilmeHistoricoResponse();
        response.setTitulo(titulo);
        response.setAno(ano);
        response.setClassificacao(classificacao);
        return response;
    }
}
