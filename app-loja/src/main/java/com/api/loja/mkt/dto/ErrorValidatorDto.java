package com.api.loja.mkt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorValidatorDto {
	
	private String campo;
	
	private String erro;

}
