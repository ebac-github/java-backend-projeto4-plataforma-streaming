package com.ebac.plataforma_streaming.controller;

import com.ebac.plataforma_streaming.requests.MigracaoPlanoRequest;
import com.ebac.plataforma_streaming.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/planos")
public class PlanoController {

    private final PlanoService service;

    public PlanoController(final PlanoService service) {
        this.service = service;
    }

    @PatchMapping("/migracao")
    public ResponseEntity<String> listarTodos(@AuthenticationPrincipal UserDetails user, @RequestBody @Valid MigracaoPlanoRequest migracaoRequest) throws Exception {
       try {
           service.migrarPlano(user, migracaoRequest);
           return ResponseEntity.ok("plano migrado.");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }

    }
}
