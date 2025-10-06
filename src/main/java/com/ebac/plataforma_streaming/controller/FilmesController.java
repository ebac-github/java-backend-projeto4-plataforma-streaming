package com.ebac.plataforma_streaming.controller;

import com.ebac.plataforma_streaming.response.FilmeHistoricoResponse;
import com.ebac.plataforma_streaming.response.FilmeResponse;
import com.ebac.plataforma_streaming.service.FilmeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/filmes")
public class FilmesController {

     private FilmeService service;

    public FilmesController(final FilmeService service) {
        this.service = service;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FilmeResponse>> listarTodos(@AuthenticationPrincipal UserDetails user) throws Exception {
        List<FilmeResponse> filmesDisponiveis = service.listarFilmesDisponiveis(user);
        return ResponseEntity.ok(filmesDisponiveis);
    }

    @GetMapping("/assistir/{idFilme}")
    public ResponseEntity<String> listarTodos(@AuthenticationPrincipal UserDetails user, @PathVariable Integer idFilme) throws Exception {
        try{
            String response = service.assistirFilme(user, idFilme);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/historico")
    public ResponseEntity<Object> listarHistoricoFilmesAssistidos(@AuthenticationPrincipal UserDetails user) {
         try {
             List<FilmeHistoricoResponse> response = service.listarHistorico(user);
             return ResponseEntity.ok(response);
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
         }
    }


}
