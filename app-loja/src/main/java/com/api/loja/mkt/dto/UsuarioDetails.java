package com.api.loja.mkt.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class UsuarioDetails extends User {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5916633150893973151L;
    private String nome;
	private String email;
	private UUID empresa;
	private Boolean isAdmin;
    private Boolean isMaster;
    private Boolean isAtivo;
    

    public UsuarioDetails(UUID empresa , String username, String senha, Boolean isAtivo, Boolean isAdmin, Boolean isMaster, Collection<? extends GrantedAuthority> permissoes) {
        super(username, senha, permissoes);
        this.email = username;
        this.isAtivo = isAtivo;
        this.isAdmin = isAdmin;
        this.isMaster = isMaster;
    }

    public UsuarioDetails(UUID empresa, String nome, String username, String senha, Boolean isAtivo, Boolean isAdmin, Boolean isMaster, Boolean contaNaoExpirada,
                          Boolean credenciaisNaoExpiradas, Boolean contaNaoBloqueada, Collection<? extends GrantedAuthority> permissoes) {
        
    	super(username, senha, isAtivo, contaNaoExpirada, credenciaisNaoExpiradas, contaNaoBloqueada, permissoes);
        this.empresa = empresa;
        this.nome = nome;
        this.email = username;
        this.isAdmin = isAdmin;
        this.isMaster = isMaster;
    }

    public UsuarioDetails( String username, String senha, boolean isAtivo, Boolean credenciaisNaoExpiradas,  Collection<? extends GrantedAuthority> permissoes) {

        super(username, senha, isAtivo, true, credenciaisNaoExpiradas, true, permissoes);
        this.email = username;
    }

}
