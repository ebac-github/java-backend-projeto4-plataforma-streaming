package com.ebac.plataforma_streaming.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioNaoAssinanteRequest {

    @NotBlank(message = "Nome do usuário não informado.")
    @Size(min = 4, message = "O nome do usuário precisa ter no mínimo 4 caracteres.")
    private String nome;

    @NotBlank(message = "Senha do usuário não informado.")
    @Size(min = 4, message = "Você precisa informar uma senha de no mínimo 4 caracteres.")
    private String senha;

    @Min(value= 0, message = "Idade inválida.")
    private Integer idade;

    private boolean permiteFilmesAdultos;

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

    public boolean isPermiteFilmesAdultos() {
        return permiteFilmesAdultos;
    }

    public void setPermiteFilmesAdultos(boolean permiteFilmesAdultos) {
        this.permiteFilmesAdultos = permiteFilmesAdultos;
    }
}
