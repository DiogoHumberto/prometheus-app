package com.api.loja.mkt.config;

import com.api.loja.mkt.dto.ErrorGenericDto;
import com.api.loja.mkt.dto.ErrorValidatorDto;
import com.api.loja.mkt.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorValidatorDto> handleNotValidException(MethodArgumentNotValidException exception) {
		
		List<ErrorValidatorDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String msg = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorValidatorDto erro = new ErrorValidatorDto(e.getField(), msg);
			dto.add(erro);
		});
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public List<ErrorValidatorDto> handleBeanPropertyBindingResultException(BindException  exception) {
				
		List<ErrorValidatorDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getFieldErrors();
		fieldErrors.forEach(e -> {
			String msg = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorValidatorDto erro = new ErrorValidatorDto(e.getField(), msg);
			dto.add(erro);
		});
		return dto;
	}	
	
	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {

		return new ResponseEntity<Object>(
				ErrorGenericDto.builder().context(request.getRequestURI()).message(ex.getLocalizedMessage())
						.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build(),
				new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
		
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleDeserializeValue(HttpMessageNotReadableException exception, HttpServletRequest request){
		
		return new ResponseEntity<Object>(
	        		ErrorGenericDto.builder()
	        		.context(request.getRequestURI())
	        		.message("Tipo de valor improprio ou não esperado, verifique BODY do request")
	        		.localMenssage(exception.getLocalizedMessage())
	        		.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler({ BadCredentialsException.class , DisabledException.class, InternalAuthenticationServiceException.class})
	public ResponseEntity<Object> badCredentialsException(Exception ex, HttpServletRequest request) {

		return new ResponseEntity<Object>(
				ErrorGenericDto.builder()
						.context(request.getRequestURI())
						.message(ex.getLocalizedMessage())
						.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleGenericException(Exception ex, HttpServletRequest request) {  	
    	
       return new ResponseEntity<Object>(
        		ErrorGenericDto.builder()
        		.context(request.getRequestURI())
        		.message(ex.getLocalizedMessage())
        		.timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)).build(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
