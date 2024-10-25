package com.api.loja.mkt.controller;

import com.api.loja.mkt.dto.FormAuthDto;
import com.api.loja.mkt.dto.TokenDto;
import com.api.loja.mkt.service.AuthService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MeterRegistry meterRegistry;

    private Counter authUserSuccess;
    private Counter authUserErrors;

    // O Lombok irá gerar o construtor automaticamente
    // Mova a lógica de criação dos contadores para um método @PostConstruct

    @PostConstruct
    public void init() {
        this.authUserSuccess = Counter.builder("auth_user_success")
                .description("usuarios autenticados")
                .register(meterRegistry);

        this.authUserErrors = Counter.builder("auth_user_error")
                .description("erros de login")
                .register(meterRegistry);
    }

    @PostMapping
    public ResponseEntity<TokenDto> autenticarApi(@RequestBody @Valid FormAuthDto formDto) {
        try {
            TokenDto token = authService.autenticar(formDto);
            authUserSuccess.increment();
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            authUserErrors.increment();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
