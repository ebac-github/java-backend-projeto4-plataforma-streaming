package com.ebac.plataforma_streaming.utils;

import com.ebac.plataforma_streaming.builder.FilmeHistoricoResponseBuilder;
import com.ebac.plataforma_streaming.builder.FilmeResponseBuilder;
import com.ebac.plataforma_streaming.entities.Filme;
import com.ebac.plataforma_streaming.enums.Plano;
import com.ebac.plataforma_streaming.response.FilmeHistoricoResponse;
import com.ebac.plataforma_streaming.response.FilmeResponse;

public class ConvertToResponse {

    public static FilmeResponse filmeToResponse(Filme f, Plano planoUsuarioAssinante){
        return FilmeResponseBuilder.create()
                                 .titulo(f.getTitulo())
                                 .plano(f.getPlanoMinimo().name())
                                 .build(planoUsuarioAssinante);
    }

    public static FilmeHistoricoResponse filmeToHistoricoResponse(Filme f){
        return FilmeHistoricoResponseBuilder.create()
                .titulo(f.getTitulo())
                .ano(f.getAno())
                .classificacao(f.getClassificacao())
                .build();
    }
}
