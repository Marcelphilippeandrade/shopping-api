package br.com.ecommerce.marcel.philippe.communication;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;

@Service
public class UsuarioService {

	public UsuarioDTO getUserByCpf(String cpf) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/user/cpf/" + cpf;
		ResponseEntity<UsuarioDTO> response = restTemplate.getForEntity(url, UsuarioDTO.class);
		return response.getBody();
	}
}
