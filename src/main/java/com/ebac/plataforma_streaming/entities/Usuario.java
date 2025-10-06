package com.ebac.plataforma_streaming.entities;

import com.ebac.plataforma_streaming.enums.Perfil;
import com.ebac.plataforma_streaming.enums.TipoUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity(name = "usuario")
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

    private static final long serializableVersionID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Integer idade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Perfil perfil;

    @Column(nullable = false)
    private Boolean permiteFilmesAdultos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "historicos_usuarios_filmes",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    private List<Filme> historicoFilmesAssistidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioAssinante usuarioAssinante;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getTipoUsuario().getAuthority()));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipoUsuario() {
        return this.tipo;
    }

    public void setTipoUsuario(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Boolean getPermiteFilmesAdultos() {
        return permiteFilmesAdultos;
    }

    public void setPermiteFilmesAdultos(Boolean permiteFilmesAdultos) {
        this.permiteFilmesAdultos = permiteFilmesAdultos;
    }

    public List<Filme> getHistoricoFilmesAssistidos() {
        return historicoFilmesAssistidos;
    }

    public void setHistoricoFilmesAssistidos(List<Filme> historicoFilmesAssistidos) {
        this.historicoFilmesAssistidos = historicoFilmesAssistidos;
    }

    public UsuarioAssinante getUsuarioAssinante() {
        return usuarioAssinante;
    }

    public void setUsuarioAssinante(UsuarioAssinante usuarioAssinante) {
        this.usuarioAssinante = usuarioAssinante;
    }
}