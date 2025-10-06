package com.ebac.plataforma_streaming.requests;

import com.ebac.plataforma_streaming.annotations.EnumPlano;
import com.ebac.plataforma_streaming.enums.Plano;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioAssinanteRequest {

    @NotBlank(message = "Nome do usuário não informado.")
    @Size(min = 4, message = "O nome do usuário precisa ter no mínimo 4 caracteres.")
    private String nome;

    @NotBlank(message = "Senha do usuário não informado.")
    @Size(min = 4, message = "Você precisa informar uma senha de no mínimo 4 caracteres.")
    private String senha;

    @Min(value= 18, message = "Você precisa ter pelo menos 18 anos para criar uma conta de assinante.")
    private Integer idade;

    @EnumPlano(enumClass = Plano.class)
    private String plano;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Plano getPlano() {
        return Plano.valueOf(plano.toUpperCase());
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }
}
