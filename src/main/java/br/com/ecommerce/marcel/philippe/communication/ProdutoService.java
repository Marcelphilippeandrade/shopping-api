package br.com.ecommerce.marcel.philippe.communication;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.ecommerce.marcel.philippe.dto.ProdutoDTO;

@Service
public class ProdutoService {

	public ProdutoDTO getProdutoByIdentifier(String produtoIdentifier) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8081/produto/" + produtoIdentifier;
		ResponseEntity<ProdutoDTO> response = restTemplate.getForEntity(url, ProdutoDTO.class);
		return response.getBody();
	}
}
