package br.com.ecommerce.marcel.philippe.communication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;

@Service
public class UsuarioService {

	@Value("${USER_API_URL:http://localhost:8080}")
	private String userApiURL;

	public UsuarioDTO getUserByCpf(String cpf, String key) {

		try {
			RestTemplate restTemplate = new RestTemplate();

			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(userApiURL + "/user/cpf/" + cpf);
			builder.queryParam("key", key);

			ResponseEntity<UsuarioDTO> response = restTemplate.getForEntity(builder.toUriString(), UsuarioDTO.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {
			throw new UsuarioNotFoundException();
		}
	}
}
