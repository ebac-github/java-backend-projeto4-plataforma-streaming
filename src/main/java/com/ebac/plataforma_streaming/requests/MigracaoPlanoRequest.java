package com.ebac.plataforma_streaming.requests;

import com.ebac.plataforma_streaming.annotations.EnumPlano;
import com.ebac.plataforma_streaming.enums.Plano;

public class MigracaoPlanoRequest {

    @EnumPlano(enumClass = Plano.class)
    private String novoPlano;

    public Plano getNovoPlano() {
        return  Plano.valueOf(novoPlano.toUpperCase());
    }

    public void setNovoPlano(String novoPlano) {
        this.novoPlano = novoPlano;
    }
}
