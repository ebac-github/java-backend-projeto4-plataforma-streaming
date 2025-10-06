package com.ebac.plataforma_streaming.config;

import com.ebac.plataforma_streaming.enums.TipoUsuario;
import com.ebac.plataforma_streaming.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new RegexRequestMatcher(".*/usuarios/criar/assinante", HttpMethod.POST.name())).permitAll()
                        .requestMatchers(new RegexRequestMatcher(".*/usuarios/criar/nao-assinante", HttpMethod.POST.name())).hasRole(TipoUsuario.ASSINANTE.name())
                        .requestMatchers(new RegexRequestMatcher(".*/usuarios/deletar/.*", HttpMethod.DELETE.name())).hasRole(TipoUsuario.ASSINANTE.name())
                        .requestMatchers(new RegexRequestMatcher(".*/planos/.*", HttpMethod.PATCH.name())).hasRole(TipoUsuario.ASSINANTE.name())
                        .anyRequest().authenticated()
                )
                .userDetailsService(usuarioService)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}