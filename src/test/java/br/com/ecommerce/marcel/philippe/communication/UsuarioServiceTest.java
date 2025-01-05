package br.com.ecommerce.marcel.philippe.communication;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.ecommerce.marcel.philippe.dto.UsuarioDTO;
import br.com.ecommerce.marcel.philippe.exception.UsuarioNotFoundException;

class UsuarioServiceTest {

	@InjectMocks
	private UsuarioService usuarioService;

	@Mock
	private RestTemplate restTemplate;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		ReflectionTestUtils.setField(usuarioService, "userApiURL", "http://localhost:8080/usuario/cpf/");
	}

	@Test
	public void deveRetornarUmUsuarioApartirDeUmCpf() {
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		String cpf = "06618938635";
		String key = "972d5ecf-81c7-49ec-89b4-70a8acc69a19";
		usuarioDTO.setCpf(cpf);

		when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(usuarioDTO, HttpStatus.OK));
		UsuarioDTO result = usuarioService.getUserByCpf(cpf, key);

		assertEquals(cpf, result.getCpf());
	}

	@Test
	public void deveRetornarUmaExcecaoCasoOUsuarioNaoEstejaCadastrado() {
		String cpf = "12345678901";
		String key = "testKey";

		when(restTemplate.getForEntity(anyString(), any()))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		assertThrows(UsuarioNotFoundException.class, () -> usuarioService.getUserByCpf(cpf, key));
	}
}