package com.api.loja.mkt.controller;

import com.api.loja.mkt.dto.FormAuthDto;
import com.api.loja.mkt.dto.TokenDto;
import com.api.loja.mkt.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;


    @PostMapping
    public ResponseEntity<TokenDto> autenticarApi(@RequestBody @Valid FormAuthDto formDto) throws AuthenticationException {

        return ResponseEntity.ok(authService.autenticar(formDto));
    }

}
