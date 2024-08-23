package br.com.ecommerce.marcel.philippe.exception.advice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import br.com.ecommerce.marcel.philippe.dto.ErrorDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;

public class ShoppingControllerAdviceTest {
	
	@InjectMocks
	private ShoppingControllerAdvice shoppingControllerAdvaice;
	
	@Mock
	private ProdutoNotFoundException produtoNotFoundException;

	@Mock
	private UsuarioNotFoundException usuarioNotFoundException;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void deveRetornarHandleProdutoNotFound() {
		when(produtoNotFoundException.getMessage()).thenReturn("Produto não encontrado.");

		ErrorDTO errorDTO = shoppingControllerAdvaice.handleProdutoNotFound(produtoNotFoundException);

		assertEquals(HttpStatus.NOT_FOUND.value(), errorDTO.getStatus());
		assertEquals("Produto não encontrado.", errorDTO.getMessage());
		assertEquals(new Date().getClass(), errorDTO.getTimestamp().getClass());
	}
	
	@Test
    public void deveRetornarHandleUsuarioNotFound() {
        when(usuarioNotFoundException.getMessage()).thenReturn("Usuário não encontrado.");

        ErrorDTO errorDTO = shoppingControllerAdvaice.handleUsuarioNotFound(usuarioNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND.value(), errorDTO.getStatus());
        assertEquals("Usuário não encontrado.", errorDTO.getMessage());
        assertEquals(new Date().getClass(), errorDTO.getTimestamp().getClass());
    }
	 
}
