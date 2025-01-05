package br.com.ecommerce.marcel.philippe.communication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;
import br.com.ecommerce.marcel.philippe.response.Response;

@Service
public class UsuarioService {

	@Value("${USER_API_URL:http://localhost:8080/usuario/cpf/}")
	private String userApiURL;

	public UsuarioDTO getUserByCpf(String cpf, String key) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = UriComponentsBuilder.fromHttpUrl(userApiURL + cpf)
                    .queryParam("key", key)
                    .toUriString();

            ResponseEntity<Response<UsuarioDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Response<UsuarioDTO>>() {});

            return response.getBody().getData();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UsuarioNotFoundException();
        }
    }
}
