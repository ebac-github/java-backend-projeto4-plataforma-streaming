package com.ebac.plataforma_streaming.enums;

public enum TipoUsuario {
    ASSINANTE, NAO_ASSINANTE;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}