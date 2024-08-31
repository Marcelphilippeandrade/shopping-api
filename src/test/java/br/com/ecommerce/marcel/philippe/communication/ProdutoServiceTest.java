package br.com.ecommerce.marcel.philippe.communication;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;

@SpringBootTest
class ProdutoServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private ProdutoService produtoService;
	
	@Value("${PRODUCT_API_URL:http://localhost:8081/produto/}")
    private String productApiURL;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		produtoService.productApiURL = productApiURL;
	}

	@Test
	public void deveRetornarUmProdutoApartirDoIdentificador() {

		ProdutoDTO produtoDTO = new ProdutoDTO();
		String produtoIdentifier = "a1";
		produtoDTO.setProdutoIdentifier(produtoIdentifier);
		String url = productApiURL + produtoIdentifier;

		when(restTemplate.getForEntity(eq(url), eq(ProdutoDTO.class))).thenReturn(new ResponseEntity<>(produtoDTO, HttpStatus.OK));
		ProdutoDTO result = produtoService.getProdutoByIdentifier(produtoIdentifier);

		assertEquals(produtoIdentifier, result.getProdutoIdentifier());
	}

	@Test
	public void deveRetornarUmaExcecaoCasoOUsuarioNaoEstejaCadastrado() {
		
		 String produtoIdentifier = "notfound";
	     String url = productApiURL + produtoIdentifier;
		
		when(restTemplate.getForEntity(eq(url), eq(ProdutoDTO.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found"));

		assertThrows(ProdutoNotFoundException.class, () -> {
			produtoService.getProdutoByIdentifier(produtoIdentifier);
		});
	}
}
