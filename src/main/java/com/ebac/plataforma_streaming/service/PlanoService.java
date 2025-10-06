package com.ebac.plataforma_streaming.service;

import com.ebac.plataforma_streaming.entities.Usuario;
import com.ebac.plataforma_streaming.enums.Plano;
import com.ebac.plataforma_streaming.exceptions.MigracaoPlanoIgualException;
import com.ebac.plataforma_streaming.repository.UsuarioAssinanteRepository;
import com.ebac.plataforma_streaming.repository.UsuarioRepository;
import com.ebac.plataforma_streaming.requests.MigracaoPlanoRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PlanoService {

    private final UsuarioAssinanteRepository usuarioAssinanteRepository;
    private final UsuarioRepository usuarioRepository;

    public PlanoService(final UsuarioAssinanteRepository usuarioAssinanteRepository, UsuarioRepository usuarioRepository) {
        this.usuarioAssinanteRepository = usuarioAssinanteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void migrarPlano(UserDetails user, @Valid MigracaoPlanoRequest migracaoRequest) throws Exception {
        Usuario usuario = usuarioRepository.findByNome(user.getUsername()).orElseThrow(() -> new Exception("Usuário assinante não existe."));
        if(usuario.getUsuarioAssinante().getPlano().equals(migracaoRequest.getNovoPlano()))
            throw new MigracaoPlanoIgualException("Você não pode migrar para o mesmo plano que você já possui. Só é possível migrar para um plano diferente do atual.");

        if((Plano.PREMIUM.equals(usuario.getUsuarioAssinante().getPlano()))
        ||  (Plano.STANDARD.equals(usuario.getUsuarioAssinante().getPlano()) && Plano.BASIC.equals(migracaoRequest.getNovoPlano()))){
            downgradeDePlano(usuario, migracaoRequest.getNovoPlano());

        } else if(Plano.BASIC.equals(usuario.getUsuarioAssinante().getPlano())
        || (Plano.STANDARD.equals(usuario.getUsuarioAssinante().getPlano()) && Plano.PREMIUM.equals(migracaoRequest.getNovoPlano()))){
            upgradeDePlano(usuario, migracaoRequest.getNovoPlano());
        }
    }

    @Transactional
    private void downgradeDePlano(Usuario usuario, Plano novoPlano) {
        usuarioRepository.deleteByUsuarioAssinanteAndIdNot(usuario.getUsuarioAssinante(), usuario.getId());
        usuario.getUsuarioAssinante().setPlano(novoPlano);
        usuarioRepository.save(usuario);
    }

    private void upgradeDePlano(Usuario usuario, Plano novoPlano) {
        usuario.getUsuarioAssinante().setPlano(novoPlano);
        usuarioRepository.save(usuario);
    }


}
