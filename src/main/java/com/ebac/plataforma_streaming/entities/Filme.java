package com.ebac.plataforma_streaming.entities;

import com.ebac.plataforma_streaming.enums.Plano;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name = "filme")
@Table(name = "filmes")
public class Filme implements Serializable {

    private static final long serializableVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filme")
    private Integer idFilme;

    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private Integer classificacao;

    @Column(nullable = false)
    private Integer duracao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Plano planoMinimo;

    @ManyToMany(mappedBy = "historicoFilmesAssistidos")
    private List<Usuario> usuariosAssistiram;

    public Integer getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Integer idFilme) {
        this.idFilme = idFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Plano getPlanoMinimo() {
        return planoMinimo;
    }

    public void setPlanoMinimo(Plano planoMinimo) {
        this.planoMinimo = planoMinimo;
    }

    public List<Usuario> getUsuariosAssistiram() {
        return usuariosAssistiram;
    }

    public void setUsuariosAssistiram(List<Usuario> usuariosAssistiram) {
        this.usuariosAssistiram = usuariosAssistiram;
    }
}
