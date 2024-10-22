package com.api.loja.mkt.service;

import com.api.loja.mkt.dto.FormAuthDto;
import com.api.loja.mkt.dto.TokenDto;
import com.api.loja.mkt.model.UserModel;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service("authService")
@RequiredArgsConstructor
public class AuthService {

    @Value("${api.jwt.expiration}")
    private String expiration;

    @Value("${api.jwt.secret}")
    private String secret;

    private SecretKey secretKey;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    @PostConstruct
    public void init() {
        var secret = Base64.getEncoder().encodeToString(this.secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    }

    public boolean isTokenValid(String token) {
        try {

            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            log.info("--> token user {} valido até : {}", claims.getBody().getSubject(),
                    claims.getBody().getExpiration());

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalido JWT token: {}", e.getMessage());
            log.trace("Invalido JWT token trace.", e);
        }

        return false;
    }

    public TokenDto autenticar(FormAuthDto formDto) throws AuthenticationException {

        UsernamePasswordAuthenticationToken dadosLogin = new UsernamePasswordAuthenticationToken(formDto.getEmail(),
                formDto.getSenha());
        Authentication authentication = authenticationManager.authenticate(dadosLogin);

        return gerarToken(authentication);

    }

    // IMPLEMENTAR REDIS
    // @Cacheable(value="Usuario", key="#token")
    public UserModel getUsuarioAutenticado(String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();

        return userService.buscaUsuarioUserName(claims.getSubject());
    }

    private TokenDto gerarToken(Authentication authentication) {

        Date dtAtual = Calendar.getInstance().getTime();

        return TokenDto.builder().type("Bearer")
                .token(Jwts.builder().
                        setIssuer("Api Eventos")
                        .setSubject(authentication.getName())
                        .setIssuedAt(dtAtual)
                        .setExpiration(createExpirationToken(dtAtual))
                        .signWith(this.secretKey, SignatureAlgorithm.HS256).compact())
                .createIn(dtAtual).expirationIn(createExpirationToken(dtAtual)).build();

    }

    private Date createExpirationToken(Date atual) {
        return new Date(atual.getTime() + Long.parseLong(expiration));
    }

}
