package com.api.loja.mkt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FormAuthDto {

	@Email(message = "e-mail invalido!")
	@NotBlank(message = "Campo obrigatorio!")
	private String email;

	@NotBlank(message = "Campo obrigatorio!")
	private String senha;
	
	private UUID uuidEmpresa;

}
