package com.ebac.plataforma_streaming.controller;

import com.ebac.plataforma_streaming.exceptions.UsuariosLimitadosPorAssinanteException;
import com.ebac.plataforma_streaming.requests.UsuarioAssinanteRequest;
import com.ebac.plataforma_streaming.requests.UsuarioNaoAssinanteRequest;
import com.ebac.plataforma_streaming.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuariosController {

    private final UsuarioService service;

    public UsuariosController(final UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/criar/assinante")
    public ResponseEntity<Void> criarAssinante(@RequestBody @Valid UsuarioAssinanteRequest request) {
        service.criarUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/criar/nao-assinante")
    public ResponseEntity<String> criarNaoAssinante(@RequestBody @Valid UsuarioNaoAssinanteRequest request, @AuthenticationPrincipal UserDetails user) throws Exception {
        try {
            service.criarUsuario(request, user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UsuariosLimitadosPorAssinanteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/assinante")
    public ResponseEntity<String> deletarAssinante(@AuthenticationPrincipal UserDetails user) {
        try {
            service.deletarUsuarioAssinante(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/nao-assinante/{nome}")
    public ResponseEntity<String> deletarNaoAssinante(@AuthenticationPrincipal UserDetails user, @PathVariable String nome) {
        try {
            service.deletarUsuarioNaoAssinante(user, nome);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
