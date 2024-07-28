package br.com.ecommerce.marcel.philippe.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;

class ProdutoServiceTest {

	@InjectMocks
	private ProdutoService produtoService;
	
	@Mock
	private RestTemplate restTemplate;
	
	@BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void deveRetornarUmProdutoApartirDoIdentificador() {
		ProdutoDTO produtoDTO = new ProdutoDTO();
		String produtoIdentifier = "tv";
		produtoDTO.setProdutoIdentifier(produtoIdentifier);
		ResponseEntity<Object> responseEntity = ResponseEntity.ok(produtoDTO);
		
		when(restTemplate.getForEntity(anyString(), any())).thenReturn(responseEntity);
		
		ProdutoDTO result = produtoService.getProdutoByIdentifier(produtoIdentifier);
		
		assertEquals(produtoIdentifier, result.getProdutoIdentifier());
	}
}
