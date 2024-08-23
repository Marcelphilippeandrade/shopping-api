package br.com.ecommerce.marcel.philippe.exception.advice;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.ecommerce.marcel.philippe.dto.ErrorDTO;
import br.com.ecommerce.marcel.philippe.exception.CategoriaNotFoundException;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;

@ControllerAdvice(basePackages = "br.com.ecommerce.marcel.philippe.controller")
public class ProdutoControllerAdvice {

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProdutoNotFoundException.class)
	public ErrorDTO handleUserNotFound(ProdutoNotFoundException userNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Produto não encontrado.");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CategoriaNotFoundException.class)
	public ErrorDTO handleCategoriaNotFound(CategoriaNotFoundException categoryNotFoundException) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
		errorDTO.setMessage("Categoria não encontrada.");
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		StringBuilder sb = new StringBuilder("Valor inválido para o(s) campo(s):");
		for (FieldError fieldError : fieldErrors) {
			sb.append(" ");
			sb.append(fieldError.getField());
			sb.append(",");
		}
		errorDTO.setMessage(sb.toString());
		errorDTO.setTimestamp(new Date());
		return errorDTO;
	}
}
