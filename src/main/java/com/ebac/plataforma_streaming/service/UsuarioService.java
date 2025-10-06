package com.ebac.plataforma_streaming.service;

import com.ebac.plataforma_streaming.entities.Usuario;
import com.ebac.plataforma_streaming.entities.UsuarioAssinante;
import com.ebac.plataforma_streaming.enums.Perfil;
import com.ebac.plataforma_streaming.enums.Plano;
import com.ebac.plataforma_streaming.enums.TipoUsuario;
import com.ebac.plataforma_streaming.exceptions.DireitosSobreUsuarioException;
import com.ebac.plataforma_streaming.exceptions.UsuariosLimitadosPorAssinanteException;
import com.ebac.plataforma_streaming.repository.UsuarioAssinanteRepository;
import com.ebac.plataforma_streaming.repository.UsuarioRepository;
import com.ebac.plataforma_streaming.requests.UsuarioAssinanteRequest;
import com.ebac.plataforma_streaming.requests.UsuarioNaoAssinanteRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;
    private final UsuarioAssinanteRepository usuarioAssinanteRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(final UsuarioRepository repository, final  PasswordEncoder passwordEncoder, final UsuarioAssinanteRepository usuarioAssinanteRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioAssinanteRepository = usuarioAssinanteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repository.findByNome(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado:" + username));
        return new User(u.getUsername(), u.getPassword(), true, true, true, true, u.getAuthorities());

    }

    @Transactional
    public void criarUsuario(UsuarioAssinanteRequest request){
        Usuario u = new Usuario();
        u.setNome(request.getNome());
        u.setSenha(passwordEncoder.encode(request.getSenha()));
        u.setIdade(request.getIdade());
        u.setPerfil(Perfil.ADULTO);
        u.setPermiteFilmesAdultos(true);
        u.setTipoUsuario(TipoUsuario.ASSINANTE);
        u  = repository.save(u);

        UsuarioAssinante ua = new UsuarioAssinante();
        ua.setPlano(request.getPlano());
        ua.setIdUsuario(u.getId());
        usuarioAssinanteRepository.save(ua);
        u.setUsuarioAssinante(ua);
        repository.save(u);
    }

    public void criarUsuario(UsuarioNaoAssinanteRequest request, UserDetails user) throws Exception {
        Usuario usuarioAssinanteRequisicao = getUsuario(user);
        UsuarioAssinante usuarioAssinante = usuarioAssinanteRepository.findByIdUsuario(usuarioAssinanteRequisicao.getId());
        long numeroUsuariosPorAssinante = repository.countByUsuarioAssinante(usuarioAssinante);
        validarQuantidadeDeUsuariosPorAssinante(usuarioAssinante, numeroUsuariosPorAssinante);

        Usuario u = new Usuario();
        u.setNome(request.getNome());
        u.setSenha(passwordEncoder.encode(request.getSenha()));
        u.setIdade(request.getIdade());
        u.setPerfil(gerarPerfil(request));
        u.setPermiteFilmesAdultos(request.isPermiteFilmesAdultos());
        u.setTipoUsuario(TipoUsuario.NAO_ASSINANTE);
        u.setUsuarioAssinante(usuarioAssinante);
        repository.save(u);
    }

    private Perfil gerarPerfil(UsuarioNaoAssinanteRequest request){
        if(request.getIdade() < 12) {
            request.setPermiteFilmesAdultos(false);
            return Perfil.INFANTIL;
        }
        else if (request.getIdade() < 18) {
            request.setPermiteFilmesAdultos(request.isPermiteFilmesAdultos());
            return Perfil.ADOLESCENTE;
        }
        else {
            request.setPermiteFilmesAdultos(true);
            return Perfil.ADULTO;
        }
    }

    private void validarQuantidadeDeUsuariosPorAssinante(UsuarioAssinante usuarioAssinante, long numeroUsuariosPorAssinante){
        if(Plano.BASIC.equals(usuarioAssinante.getPlano())
                || Plano.STANDARD.equals(usuarioAssinante.getPlano()) && numeroUsuariosPorAssinante >= 3
                || Plano.PREMIUM.equals(usuarioAssinante.getPlano()) && numeroUsuariosPorAssinante >= 5)
            throw new UsuariosLimitadosPorAssinanteException("Você não pode adicionar mais usuários ao seu plano.");
    }

    @Transactional
    public void deletarUsuarioAssinante(UserDetails user) throws Exception {
          Usuario usuario = getUsuario(user);
          repository.deleteByUsuarioAssinante(usuario.getUsuarioAssinante());
          usuarioAssinanteRepository.delete(usuario.getUsuarioAssinante());
    }

    @Transactional
    public void deletarUsuarioNaoAssinante(UserDetails user, String nomeUsuarioADeletar) throws Exception {
        Usuario usuarioAssinante = getUsuario(user);
        Usuario usuarioADeletar = repository.findByNome(nomeUsuarioADeletar)
                                            .orElseThrow(() -> new Exception("Usuário com esse nome não existe."));

        if(!usuarioAssinante.getUsuarioAssinante().getId().equals(usuarioADeletar.getUsuarioAssinante().getId()))
            throw new DireitosSobreUsuarioException("Você não possui direito para excluir este usuário, somente os usuários vinculados ao seu.");

        repository.delete(usuarioADeletar);
    }

    private Usuario getUsuario(UserDetails user) throws Exception {
        return repository.findByNome(user.getUsername()).orElseThrow(() -> new Exception("Usuário assinante não existe."));
    }
}
