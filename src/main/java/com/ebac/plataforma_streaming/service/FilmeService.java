package com.ebac.plataforma_streaming.service;

import com.ebac.plataforma_streaming.entities.Filme;
import com.ebac.plataforma_streaming.entities.Usuario;
import com.ebac.plataforma_streaming.enums.Perfil;
import com.ebac.plataforma_streaming.enums.Plano;
import com.ebac.plataforma_streaming.exceptions.FilmeIndisponivelException;
import com.ebac.plataforma_streaming.repository.FilmeRepository;
import com.ebac.plataforma_streaming.repository.UsuarioRepository;
import com.ebac.plataforma_streaming.response.FilmeHistoricoResponse;
import com.ebac.plataforma_streaming.response.FilmeResponse;
import com.ebac.plataforma_streaming.utils.ConvertToResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    private final FilmeRepository repository;
    private final UsuarioRepository usuarioRepository;

    public FilmeService(final FilmeRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<FilmeResponse> listarFilmesDisponiveis(UserDetails user) throws Exception {

        Usuario usuario = usuarioRepository.findByNome(user.getUsername()).orElseThrow(() -> new Exception("Usuário assinante não existe."));
        Plano plano = usuario.getUsuarioAssinante().getPlano();

        List<Filme> filmes = filtrarPorPerfil(usuario, getFilmesByPlano(plano));
        List<FilmeResponse> response = new ArrayList<>();

        filmes.forEach(f -> response.add(ConvertToResponse.filmeToResponse(f, plano)));

        return response;
    }

    private List<Filme> filtrarPorPerfil(Usuario usuario, List<Filme> filmes) {

        if(usuario.getPermiteFilmesAdultos())
            return filmes;
        else if(Perfil.ADOLESCENTE.equals(usuario.getPerfil()))
            return filmes.stream().filter(f -> f.getClassificacao() <= 16).collect(Collectors.toList());
        else
            return filmes.stream().filter(f -> f.getClassificacao() <= 12).collect(Collectors.toList());
    }

    private List<Filme> getFilmesByPlano(Plano plano){

        List<Plano> buscaPorPlano = null;

        if(Plano.BASIC.equals(plano)) buscaPorPlano = List.of(Plano.BASIC);
        else if (Plano.STANDARD.equals(plano)) buscaPorPlano =  List.of(Plano.BASIC, Plano.STANDARD);
        else return repository.findAll();

        return repository.findByPlanoMinimoIn(buscaPorPlano);
    }

    @Transactional
    public String assistirFilme(UserDetails user, Integer idFilme) throws Exception {
        Usuario usuario = usuarioRepository.findByNome(user.getUsername()).orElseThrow(() -> new Exception("Usuário não existe."));
        Filme filme = repository.findById(idFilme).orElseThrow(() -> new Exception("Filme não existe."));

        validarDisponibilidadeParaUsuario(usuario, filme);

        Thread.sleep(Duration.ofSeconds(filme.getDuracao()).toMillis());
        usuario.getHistoricoFilmesAssistidos().add(filme);

        usuarioRepository.save(usuario);
        return "Filme " + filme.getTitulo() + " terminado.";
    }

    private void validarDisponibilidadeParaUsuario(Usuario usuario, Filme filme) {

        if((!filme.getPlanoMinimo().equals(Plano.BASIC) && usuario.getUsuarioAssinante().getPlano().equals(Plano.BASIC))
        || filme.getPlanoMinimo().equals(Plano.PREMIUM) && usuario.getUsuarioAssinante().getPlano().equals(Plano.STANDARD))
            throw new FilmeIndisponivelException("Este filme só está disponível para usuários no mínimo do plano " +
                    filme.getPlanoMinimo() + ". Considere migrar o seu plano.");

        if((filme.getClassificacao() > 16 && (!usuario.getPermiteFilmesAdultos()))
        || filme.getClassificacao() > 12 && Perfil.INFANTIL.equals(usuario.getPerfil())){
            throw new FilmeIndisponivelException("Seu usuário não possui perfil etário para assistir um filme com esta classificação. Contate o usuário assinante ou o administrador.");
        }
    }

    public List<FilmeHistoricoResponse> listarHistorico(UserDetails user) throws Exception {
        Usuario usuario = usuarioRepository.findByNome(user.getUsername()).orElseThrow(() -> new Exception("Usuário não existe."));
        List<Filme> filmesAssistidos = usuario.getHistoricoFilmesAssistidos();
        List<FilmeHistoricoResponse> response = new ArrayList<>();
        filmesAssistidos.forEach(f -> response.add(ConvertToResponse.filmeToHistoricoResponse(f)));
        return response;
    }
}
