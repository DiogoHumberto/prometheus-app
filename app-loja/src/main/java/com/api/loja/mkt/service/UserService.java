package com.api.loja.mkt.service;

import com.api.loja.mkt.dto.UsuarioDetails;
import com.api.loja.mkt.exception.BadRequestException;
import com.api.loja.mkt.model.UserModel;
import com.api.loja.mkt.repository.UserTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserTokenRepository userTokenRepository;

    public UserService(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    public UserModel buscaUsuarioUserName(String email) {

        Optional<UserModel> user = userTokenRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new BadRequestException("usuário não encontrado!");
        }

        return user.get();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserModel>  usuario = userTokenRepository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new BadRequestException("not fund!");

        }
        var usuarioModel = usuario.get();


        return new UsuarioDetails(
                usuarioModel.getEmail(),
                usuarioModel.getPassword(),
                true,
                true,
                AuthorityUtils.NO_AUTHORITIES);
    }
}
