package com.api.loja.mkt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class ErrorGenericDto {

	private Integer code;
	
	private String context;
	
	private String message;
	
	private String localMenssage;
	
	private String timestamp;
	
}
