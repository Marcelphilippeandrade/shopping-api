package br.com.ecommerce.marcel.philippe.communication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;
import br.com.ecommerce.marcel.philippe.exception.ProdutoNotFoundException;

@Service
public class ProdutoService {
	
	@Value("${PRODUCT_API_URL:http://localhost:8081/produto/}")
	protected String productApiURL;

	public ProdutoDTO getProdutoByIdentifier(String produtoIdentifier) {

		try {
			RestTemplate restTemplate = new RestTemplate();
			String url = productApiURL + produtoIdentifier;
			ResponseEntity<ProdutoDTO> response = restTemplate.getForEntity(url, ProdutoDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new ProdutoNotFoundException();
		}
	}
}
