package br.com.ecommerce.marcel.philippe.exception.advice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import br.com.ecommerce.marcel.philippe.dto.ErrorDTO;
import br.com.ecommerce.marcel.philippe.exception.CategoriaNotFoundException;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;

public class PrdodutoControllerAdviceTest {
	
	 private final ProdutoControllerAdvice advice = new ProdutoControllerAdvice();

	    @Test
	    public void handleUserNotFoundTest() {
	        ProdutoNotFoundException exception = new ProdutoNotFoundException();

	        ErrorDTO response = advice.handleUserNotFound(exception);

	        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	        assertEquals("Produto não encontrado.", response.getMessage());
	        assertEquals(true, response.getTimestamp() != null);
	    }

	    @Test
	    public void handleCategoriaNotFoundTest() {
	        CategoriaNotFoundException exception = new CategoriaNotFoundException();

	        ErrorDTO response = advice.handleCategoriaNotFound(exception);

	        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	        assertEquals("Categoria não encontrada.", response.getMessage());
	        assertEquals(true, response.getTimestamp() != null);
	    }

	    @Test
	    public void processValidationErrorTest() {
	        BindingResult bindingResult = mock(BindingResult.class);
	        FieldError fieldError = new FieldError("objectName", "field", "defaultMessage");
	        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

	        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

	        ErrorDTO response = advice.processValidationError(exception);

	        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	        assertEquals("Valor inválido para o(s) campo(s): field,", response.getMessage());
	        assertEquals(true, response.getTimestamp() != null);
	    }

}
